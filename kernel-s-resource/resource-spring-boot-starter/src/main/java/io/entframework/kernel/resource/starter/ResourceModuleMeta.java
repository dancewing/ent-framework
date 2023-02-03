package io.entframework.kernel.resource.starter;

import io.entframework.kernel.resource.api.constants.ResourceConstants;
import io.entframework.kernel.rule.plugin.AbstractModuleMeta;

public class ResourceModuleMeta extends AbstractModuleMeta {

    public ResourceModuleMeta() {
        super(ResourceConstants.RESOURCE_MODULE_NAME, ResourceConstants.FLYWAY_TABLE_SUFFIX, ResourceConstants.FLYWAY_LOCATIONS);
    }
}
