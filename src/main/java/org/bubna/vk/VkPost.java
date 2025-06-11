package org.bubna.vk;

import com.vk.api.sdk.objects.ads.Post;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class VkPost extends PanacheEntityBase {

    @Id
    @Column(unique = true)
    // Совмещённый id-шник для того чтобы не отправить одни и те же посты 🥴. Формируется через _.
    private String groupPostId;

    public VkPost(Post post) {
        this.groupPostId = getIdFromPostFromVk(post);
    }

    public static boolean existsById(String id) {
        return findByIdOptional(id).isPresent();
    }

    public static boolean postFromVkAlreadyUpload(Post post) {
        return existsById(getIdFromPostFromVk(post));
    }

    private static String getIdFromPostFromVk(Post post) {
        return post.getFromId() + "_" + post.getId();
    }

}
