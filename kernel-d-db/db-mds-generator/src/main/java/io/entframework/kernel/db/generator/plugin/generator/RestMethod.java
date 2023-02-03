package io.entframework.kernel.db.generator.plugin.generator;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;


public class RestMethod extends Method {

    private String httpMethod = "GET";
    private String url = "";
    private String operation;

    private FullyQualifiedJavaType recordType;

    public RestMethod(String name) {
        super(name);
    }

    public RestMethod(Method original) {
        super(original);
    }

    public RestMethod(String name, String httpMethod, FullyQualifiedJavaType recordType) {
        super(name);
        this.httpMethod = httpMethod;
        this.recordType = recordType;
    }


    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getRestPath() {
        String url = this.getUrl();
        if (StringUtils.isEmpty(url)) {
            url = "/"+ CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, this.getName());
        }
        String modelObjectName = this.recordType.getShortName();
        return "/"+ CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, modelObjectName) + url;
    }
}
