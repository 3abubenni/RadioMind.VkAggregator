package org.bubna.taskmanager.data.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image extends Content {

    private String url;

    private String text;

    public Image() {
        setType(ContentType.IMAGE);
    }

}
