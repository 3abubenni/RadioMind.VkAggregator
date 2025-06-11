package org.bubna.taskmanager.data.content;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Content {

    private ContentType type;

}
