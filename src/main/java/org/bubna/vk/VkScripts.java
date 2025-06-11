package org.bubna.vk;

public class VkScripts {

    public static String getPostOfGroup(long groupId) {
        return String.format("""
                return API.wall.get({"domain":-%s}).items;
                """, groupId);
    }

}
