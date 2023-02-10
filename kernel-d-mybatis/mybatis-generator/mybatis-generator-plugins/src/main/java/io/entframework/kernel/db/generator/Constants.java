package io.entframework.kernel.db.generator;

public final class Constants {

    public final static String DEFAULT_POJO_REQUEST_SUFFIX = "Request";
    public final static String DEFAULT_POJO_RESPONSE_SUFFIX = "Response";
    public final static String DEFAULT_MAPSTRUCT_SUFFIX = "Converter";
    public final static String DEFAULT_SERVICE_SUFFIX = "Service";
    public final static String DEFAULT_REPOSITORY_SUFFIX = "Repository";

    public final static String DEFAULT_BASE_SERVICE_PREFIX = "Base";

    public final static String DEFAULT_BASE_CONTROLLER_PREFIX = "Base";

    public final static String KERNEL_BASE_REQUEST = "io.entframework.kernel.rule.pojo.request.BaseRequest";
    public final static String KERNEL_BASE_RESPONSE = "io.entframework.kernel.db.api.pojo.response.BaseResponse";

    public final static String RESPONSE_BODY_WRAPPER = "io.entframework.kernel.rule.pojo.response.ResponseData";
    public final static String RESPONSE_BODY_SUCCESS_STATIC_METHOD = "ok";

    public final static String GENERATED_CODE_STYLE = "ENT";

    public final static String TABLE_ENUM_COLUMN_ATTR = "TABLE_ENUM_COLUMN_ATTR";

    public final static String TABLE_ENUM_FIELD_ATTR_SOURCE = "TABLE_ENUM_FIELD_ATTR_SOURCE";
    public final static String TABLE_ENUM_FIELD_ATTR = "TABLE_ENUM_FIELD_ATTR";

    public final static String WEB_PROJECT_ROOT_ALIAS = "WEB_PROJECT_ROOT_ALIAS";

    //INTROSPECTED_TABLE 关联的 Top level class， 存放在INTROSPECTED_TABLE的attributes中
    public final static String INTROSPECTED_TABLE_MODEL_CLASS = "INTROSPECTED_TABLE_MODEL_CLASS";

    public final static String INTROSPECTED_TABLE_WRAPPER_TYPESCRIPT_CLASS = "INTROSPECTED_TABLE_WRAPPER_TYPESCRIPT_CLASS";

    //Column 关联的 Field, 存放在 Field 的Attribute中
    public final static String FIELD_RELATION = "FIELD_RELATION";

    public final static String TARGET_FIELD_RELATION = "TARGET_FIELD_RELATION";
    public final static String FIELD_LOGIC_DELETE_ATTR = "FIELD_LOGIC_DELETE_ATTR";
    public final static String FIELD_VERSION_ATTR = "FIELD_VERSION_ATTR";

    //扩展字段，BaseEntity中updateUser, updateTime之类
    public final static String FIELD_EXT_ATTR = "FIELD_EXT_ATTR";

    public final static String PARENT_ENTITY_CLASS = "PARENT_ENTITY_CLASS";

    public final static String PARENT_REQUEST_CLASS = "PARENT_REQUEST_CLASS";

}
