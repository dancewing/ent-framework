package io.entframework.kernel.system.api.enums;

import io.entframework.kernel.rule.annotation.EnumHandler;
import io.entframework.kernel.rule.annotation.EnumValue;
import io.entframework.kernel.rule.enums.SupperEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@EnumHandler
@Getter
public enum MenuTypeEnum implements SupperEnum<Integer> {
    DIRECTORY(0, "目录"),
    ROUTER(1, "路由菜单"),
    LINK(2, "外部链接"),
    BUTTON(3, "按钮");

    @EnumValue
    @JsonValue
    private final Integer value;

    private final String label;

    MenuTypeEnum(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @JsonCreator
    public static MenuTypeEnum valueToEnum(Integer value) {
        return SupperEnum.fromValue(MenuTypeEnum.class, value);
    }
}
