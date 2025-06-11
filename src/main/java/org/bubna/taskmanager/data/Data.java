package org.bubna.taskmanager.data;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bubna.taskmanager.data.content.Content;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@lombok.Data
public class Data {

    private String sourceUrl;

    private List<Content> content = new ArrayList<>();

    public Data(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        content = new ArrayList<>();
    }

    public void addContent(Content content) {
        this.content.add(content);
    }

}
