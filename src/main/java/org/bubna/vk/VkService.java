package org.bubna.vk;

import com.google.gson.JsonElement;
import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.ads.Post;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bubna.taskmanager.TaskManagerClient;
import org.bubna.taskmanager.data.Data;
import org.bubna.taskmanager.data.DataMapper;
import org.bubna.util.JsonUtil;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Collections;
import java.util.List;

import static org.bubna.vk.VkScripts.getPostOfGroup;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class VkService {

    private final VkApiClient vk;

    private final UserActor userActor;

    private final DataMapper dataMapper;

    @RestClient
    protected TaskManagerClient taskManagerClient;

    @ConfigProperty(name = "vk.delta-fetching-time")
    protected long deltaFetchingTime;

    private long nextFetchingTime = -1L;

    public void uploadLastGroupPosts(long groupId) {
        log.info("Uploading last group posts for group with id {}", groupId);
        List<Post> fetchedPosts = fetchListEntitiesFromVk(getPostOfGroup(groupId), Post.class)
                .stream()
                .filter(post -> !VkPost.postFromVkAlreadyUpload(post))
                .toList();

        log.info("Fetched posts from vk: {}", fetchedPosts.size());
        List<Data> data = dataMapper.mapListFromVkPosts(fetchedPosts);
        for (Data d : data) {
            taskManagerClient.postData(d);
        }

        for (Post fetchedPost : fetchedPosts) {
            new VkPost(fetchedPost).persist();
        }
    }

    private <T> T fetchEntityFromVk(String script, Class<T> clazz) {
        JsonElement fetched = fetchJsonFromVk(script);
        if (fetched.isJsonNull()) {
            return null;
        }
        return JsonUtil.fromJson(fetched.toString(), clazz);
    }

    private <T> List<T> fetchListEntitiesFromVk(String script, Class<T> clazz) {

        JsonElement fetched = fetchJsonFromVk(script);
        if (fetched.isJsonNull()) {
            return Collections.emptyList();
        }
        if (!fetched.isJsonArray()) {
            log.error("fetchListEntitiesFromVk returned a non-array");
            throw new RuntimeException("Expected JSON array but got: " + fetched);
        }
        log.info("Fetched list entities from vk: {}", fetched.getAsJsonArray().size());
        return JsonUtil.listFromJson(fetched, clazz);
    }

    private JsonElement fetchJsonFromVk(String script) {
        waitForNextFetchingTime();
        log.info("Fetching JSON from vk: {}", script);
        try {
            return vk.execute()
                    .code(userActor, script)
                    .lang(Lang.RU)
                    .execute();
        } catch (ClientException e) {
            log.error("ClientException during executing script: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (ApiException e) {
            log.error("ApiException during executing script: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private synchronized void waitForNextFetchingTime() {
        long currentTime = System.currentTimeMillis();
        long waitTime = (nextFetchingTime - currentTime) - deltaFetchingTime;
        if (waitTime > 0) {
            sleep(waitTime);
            currentTime = System.currentTimeMillis();
        }
        nextFetchingTime = currentTime + deltaFetchingTime;
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            log.error("Error during sleep: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
