package io.entframework.kernel.db.generator.plugin.web.runtime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderClassName = "Builder")
public class ModelObject {
    private String name;
    private String type;
    private String description;
    private String camelModelName;
}
