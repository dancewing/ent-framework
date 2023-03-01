/*
 * ******************************************************************************
 *  * Copyright (c) 2023. Licensed under the Apache License, Version 2.0.
 *  *****************************************************************************
 *
 */

package io.entframework.kernel.db.generator.plugin.web;

import io.entframework.kernel.db.generator.Constants;
import io.entframework.kernel.db.generator.plugin.AbstractDynamicSQLPlugin;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.WriteMode;

import java.util.List;

public class AbstractWebPlugin extends AbstractDynamicSQLPlugin {

    /** global properties set in context -- start **/
    protected String projectRootAlias = "";

    protected String typescriptModelPackage;

    protected String apiTargetPackage;

    protected String enumTargetPackage;

    protected String codingStyle;

    /** global properties set in context -- end **/
    @Override
    public boolean validate(List<String> warnings) {
        if (!"MyBatis3DynamicSql".equalsIgnoreCase(context.getTargetRuntime())) { //$NON-NLS-1$
            warnings.add("目前支持 runtime=MyBatis3DynamicSql"); //$NON-NLS-1$
            return false;
        }

        this.typescriptModelPackage = this.context.getJavaModelGeneratorConfiguration().getTargetPackage();
        if (StringUtils.isAnyEmpty(this.typescriptModelPackage)) {
            warnings.add("请在javaModelGenerator节点中targetPackage属性");
            return false;
        }

        this.apiTargetPackage = this.context.getProperty("apiTargetPackage");
        this.enumTargetPackage = this.context.getProperty("enumTargetPackage");

        codingStyle = this.context.getProperty("generatedCodeStyle");
        if (StringUtils.isEmpty(codingStyle)) {
            codingStyle = Constants.GENERATED_CODE_STYLE;
        }

        this.projectRootAlias = this.context.getProperty("projectRootAlias");
        String mode = this.properties.getProperty("writeMode");
        if (StringUtils.isNotEmpty(mode)) {
            WriteMode writeMode = convert(mode);
            if (writeMode != null) {
                this.writeMode = writeMode;
            }
            else {
                warnings.add(this.getClass().getName() + "配置了错误的WriteMode, 可用值: NEVER,OVER_WRITE,SKIP_ON_EXIST");
                return false;
            }
        }
        return true;
    }

}
