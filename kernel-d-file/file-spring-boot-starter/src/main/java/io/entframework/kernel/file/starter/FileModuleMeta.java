package io.entframework.kernel.file.starter;

import io.entframework.kernel.file.api.constants.FileConstants;
import io.entframework.kernel.rule.plugin.AbstractModuleMeta;

public class FileModuleMeta extends AbstractModuleMeta {

    public FileModuleMeta() {
        super(FileConstants.FILE_MODULE_NAME, FileConstants.FLYWAY_TABLE_SUFFIX,
                FileConstants.FLYWAY_LOCATIONS);
    }
}
