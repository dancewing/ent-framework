package io.entframework.kernel.db.generator.plugin.web.runtime;

import io.entframework.kernel.db.generator.plugin.web.runtime.freemarker.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.GeneratedFile;
import org.mybatis.generator.api.WriteMode;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.Map;

public class TemplateGeneratedFile extends GeneratedFile {

    private final TopLevelClass topLevelClass;

    private final String fileEncoding = "utf-8";

    private final Map<String, Object> data;

    private final String templatePath;

    private final String fileName;

    private final String fileExt;

    public TemplateGeneratedFile(TopLevelClass topLevelClass, String targetProject, Map<String, Object> data,
            String templatePath, String fileName, String fileExt) {
        super(targetProject);
        this.topLevelClass = topLevelClass;
        this.data = data;
        this.templatePath = templatePath;
        this.fileName = fileName;
        this.fileExt = fileExt;
    }

    @Override
    public String getFormattedContent() {
        try {
            String content = FreemarkerTemplateEngine.process(data, this.templatePath);
            return content;
        }
        catch (Exception e) {
            e.printStackTrace();
            // throw new RuntimeException(e);
        }
        return "error generate content";
    }

    @Override
    public String getFileName() {
        String shortName = this.topLevelClass.getType().getShortNameWithoutTypeArguments();
        String camelCaseName = JavaBeansUtil.convertCamelCase(shortName, "-");
        if (StringUtils.isNotEmpty(this.fileName)) {
            return String.format(this.fileName, camelCaseName) + this.fileExt;
        }
        else {
            return camelCaseName + this.fileExt; // $NON-NLS-1$
        }
    }

    @Override
    public String getTargetPackage() {
        return this.topLevelClass.getType().getPackageName();
    }

    /**
     * This method is required by the Eclipse Java merger. If you are not running in
     * Eclipse, or some other system that implements the Java merge function, you may
     * return null from this method.
     * @return the CompilationUnit associated with this file, or null if the file is not
     * mergeable.
     */
    public CompilationUnit getCompilationUnit() {
        return this.topLevelClass;
    }

    /**
     * A Java file is mergeable if the getCompilationUnit() method returns a valid
     * compilation unit.
     * @return true, if is mergeable
     */
    @Override
    public boolean isMergeable() {
        return true;
    }

    @Override
    public String getFileEncoding() {
        return fileEncoding;
    }

    @Override
    public WriteMode getWriteMode() {
        return this.topLevelClass.getWriteMode();
    }

}
