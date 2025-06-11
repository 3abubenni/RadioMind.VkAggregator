package org.bubna.taskmanager.data;

import com.vk.api.sdk.objects.ads.Post;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.bubna.taskmanager.data.content.Text;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class DataMapper {

    private static final Logger log = LoggerFactory.getLogger(DataMapper.class);
    @ConfigProperty(name = "vk.url")
    protected String vkUrl;

    private final ContentMapper contentMapper;

    public Data mapFromVkPost(Post vkPost) {
        log.info("Map vk post: {}", vkPost);
        Data data = new Data();
        data.setSourceUrl(String.format(
                "%s/wall%d_%d",
                vkUrl,
                vkPost.getFromId(),
                vkPost.getId()
        ));
        if (vkPost.getText() != null) {
            log.info("Found text in vk post: {}", vkPost.getText());
            data.addContent(new Text(vkPost.getText()));
        }

        for (WallpostAttachment attachment : vkPost.getAttachments()) {
            if (attachment != null) {
                log.info("Found attachment in vk post: {}", attachment);
                data.addContent(contentMapper.mapVkWallAttachment(attachment));
            }
        }
        return data;
    }

    public List<Data> mapListFromVkPosts(Collection<Post> vkPosts) {

        return vkPosts.stream().map(this::mapFromVkPost).toList();
    }

}
