package org.bubna.taskmanager.data.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Audio extends Content {

    private String url;

    private int duration;

    public Audio() {
        setType(ContentType.AUDIO);
    }

}
