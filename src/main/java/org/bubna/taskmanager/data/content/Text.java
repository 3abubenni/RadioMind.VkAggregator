package org.bubna.taskmanager.data.content;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Text extends Content {

    private String text;

    public Text() {
        setType(ContentType.TEXT);
    }

    public Text(String text) {
        setType(ContentType.TEXT);
        this.text = text;
    }

}
