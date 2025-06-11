package org.bubna.vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped

public class VkConfig {

    @ConfigProperty(name = "vk.token")
    protected String token;

    @ConfigProperty(name = "vk.user-id")
    protected Long id;

    @Produces
    public UserActor userActor() {
        return new UserActor(id, token);
    }

    @Produces
    public VkApiClient vkApiClient() {
        TransportClient transportClient = new HttpTransportClient();
        return new VkApiClient(transportClient);
    }

}
