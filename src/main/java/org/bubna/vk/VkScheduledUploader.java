package org.bubna.vk;


import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class VkScheduledUploader {

    private final VkService vkService;

    @ConfigProperty(name = "vk.group-ids")
    protected List<Long> groupIds;

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void uploadNewPosts() {
        log.info("Uploading new posts");
        for (Long groupId : groupIds) {
            vkService.uploadLastGroupPosts(groupId);
        }
    }

}
