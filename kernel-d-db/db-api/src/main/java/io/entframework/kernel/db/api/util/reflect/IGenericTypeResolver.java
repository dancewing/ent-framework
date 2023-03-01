package io.entframework.kernel.db.api.util.reflect;

public interface IGenericTypeResolver {

    Class<?>[] resolveTypeArguments(final Class<?> clazz, final Class<?> genericIfc);

}
