package io.entframework.kernel.i18n.starter;

import io.entframework.kernel.i18n.api.constants.TranslationConstants;
import io.entframework.kernel.rule.plugin.AbstractModuleMeta;

public class TranslationModuleMeta extends AbstractModuleMeta {

    public TranslationModuleMeta() {
        super(TranslationConstants.I18N_MODULE_NAME, TranslationConstants.FLYWAY_TABLE_SUFFIX,
                TranslationConstants.FLYWAY_LOCATIONS);
    }
}
