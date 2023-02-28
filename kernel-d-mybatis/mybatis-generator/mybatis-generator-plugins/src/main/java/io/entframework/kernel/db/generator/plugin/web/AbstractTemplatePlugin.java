package io.entframework.kernel.db.generator.plugin.web;

import io.entframework.kernel.db.generator.plugin.web.runtime.freemarker.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class AbstractTemplatePlugin extends AbstractWebPlugin {
    private static final String VAR_START = "var_";
    protected String templatePath;
    protected String fileName;
    protected String fileExt;
    protected String modelPath;
    protected String targetPackage;

    protected boolean enableSubPackages = true;

    public boolean validate(List<String> warnings) {

        boolean validate = super.validate(warnings);

        List<String> errors = new ArrayList<>();

        String templateBaseDir = this.context.getProperty("templateBaseDir");
        if (StringUtils.isEmpty(templateBaseDir)) {
            errors.add("请设置全局变量templateBaseDir方便Freemarker初始化");
        }
        File file = new File(templateBaseDir);
        if (!file.isDirectory()) {
            errors.add(String.format("全局变量templateBaseDir: %s不可访问", templateBaseDir));
        }

        try {
            FreemarkerTemplateEngine.init(templateBaseDir);
        } catch (IOException e) {
            errors.add("Freemarker初始化失败");
            log.error(e.getMessage());
        }

        this.targetPackage = this.properties.getProperty("targetPackage");
        this.fileName = this.properties.getProperty("fileName");
        this.fileExt = this.properties.getProperty("fileExt");
        this.templatePath = this.properties.getProperty("templatePath");

        if (StringUtils.isNotEmpty(this.typescriptModelPackage)) {
            this.modelPath = StringUtils.replace(this.typescriptModelPackage, ".", "/");
        }
        if (StringUtils.isAnyEmpty(this.targetPackage, this.templatePath, this.fileName, this.fileExt)) {
            errors.add("请检查" + this.getClass().getName() + ", targetPackage | templatePath | fileName | fileExt 配置");
        }

        String enableSubPackages = this.properties.getProperty("enableSubPackages");
        if (StringUtils.isNotEmpty(enableSubPackages) && "false".equalsIgnoreCase(enableSubPackages)) {
            this.enableSubPackages = false;
        }

        if (errors.size() > 0) {
            warnings.addAll(errors);
            return false;
        }

        return validate;
    }

    /**
     * 把配置在插件属性中以var_开头的变量组装成Map, 以供模板引擎试用
     * @return Map
     */
    protected Map<String, String> getAdditionalPropertyMap() {
        Map<String, String> map = new HashMap<>();
        Enumeration enu = this.properties.keys();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            if (StringUtils.startsWith(key, VAR_START)) {
                map.put(StringUtils.substringAfter(key, VAR_START), this.properties.getProperty(key));
            }
        }
        return map;
    }
}
