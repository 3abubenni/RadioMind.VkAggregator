package org.bubna.taskmanager.data;

import com.vk.api.sdk.objects.photos.PhotoSizes;
import com.vk.api.sdk.objects.wall.WallpostAttachment;
import jakarta.enterprise.context.ApplicationScoped;
import org.bubna.taskmanager.data.content.*;

import java.util.Comparator;

@ApplicationScoped
public class ContentMapper {

    public Content mapVkWallAttachment(WallpostAttachment attachment) {
        return switch (attachment.getType()) {
            case PHOTO -> mapPhoto(attachment);
            case AUDIO -> mapAudio(attachment);
            default -> null;
        };
    }

    private Content mapAudio(WallpostAttachment attachment) {
        Audio audio = new Audio();
        if (attachment.getAudio().getUrl() != null) {
            audio.setUrl(attachment.getAudio().getUrl().getPath());
        }
        audio.setDuration(attachment.getAudio().getDuration());
        return audio;
    }

    private Image mapPhoto(WallpostAttachment attachment) {
        Image photo = new Image();
        PhotoSizes photoSizes = attachment.getPhoto()
                .getSizes()
                .stream()
                .max(Comparator.comparing(PhotoSizes::getHeight))
                .get();

        if (photoSizes.getUrl() != null) {
            photo.setUrl(photoSizes.getUrl().toString());
        } else if (photoSizes.getSrc() != null) {
            photo.setUrl(photoSizes.getSrc().toString());
        }

        photo.setText(attachment.getPhoto().getText());
        return photo;
    }

}
