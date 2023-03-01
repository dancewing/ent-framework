package io.entframework.kernel.rule.enums;

public interface SupperEnum<E> {

	String getLabel();

	E getValue();

	static <T extends SupperEnum<?>> T fromValue(Class<T> cls, Object value) {
		for (T object : cls.getEnumConstants()) {
			if (value.equals(object.getValue())) {
				return object;
			}
		}
		return null;
	}

}
