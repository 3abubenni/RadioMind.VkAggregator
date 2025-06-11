package org.bubna.taskmanager.data.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Video extends Content {

    private String url;

    private int duration;

    private String firstFrameUrl;

    private String description;

    public Video() {
        setType(ContentType.VIDEO);
    }

}
