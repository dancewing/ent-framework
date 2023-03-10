package org.mybatis.dynamic.sql.util;

import org.mybatis.dynamic.sql.exception.DynamicSqlException;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

public final class ReflectUtils {

    /**
     * class field cache
     */
    private static final Map<Class<?>, List<Field>> CLASS_FIELD_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Method[]> METHODS_CACHE = new ConcurrentHashMap<>();

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_TO_WRAPPER_MAP = new IdentityHashMap<>(8);

    static {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);
        for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_WRAPPER_TYPE_MAP.entrySet()) {
            PRIMITIVE_TYPE_TO_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * 获取字段值
     * @param entity 实体
     * @param fieldName 字段名称
     * @return 属性值
     */
    public static Object getFieldValue(Object entity, String fieldName) {
        Assert.notNull(entity, "entity can't be null");
        Assert.hasText(fieldName, "fieldName must be valid");
        Class<?> cls = entity.getClass();
        Map<String, Field> fieldMaps = getFieldMap(cls);
        try {
            Field field = fieldMaps.get(fieldName);
            assert field != null;
            field.setAccessible(true);
            return field.get(entity);
        }
        catch (ReflectiveOperationException e) {
            throw new DynamicSqlException("cant read field: " + fieldName + " from object :" + entity.getClass());
        }
    }

    /**
     * entity 是否存在字段
     * @param entity
     * @param fieldName
     * @return
     */
    public static boolean hasField(Object entity, String fieldName) {
        Class<?> cls = entity.getClass();
        Map<String, Field> fieldMaps = getFieldMap(cls);
        return fieldMaps.containsKey(fieldName);
    }

    public static void setFieldValue(Object entity, String fieldName, Object fieldValue) {
        assert entity != null;
        assert fieldName != null;
        Class<?> cls = entity.getClass();
        Map<String, Field> fieldMaps = getFieldMap(cls);
        try {
            Field field = fieldMaps.get(fieldName);
            assert field != null;
            field.setAccessible(true);
            field.set(entity, fieldValue);
        }
        catch (ReflectiveOperationException e) {
            throw new DynamicSqlException("cant set field : " + fieldName + " value to object :" + entity.getClass());
        }
    }

    /**
     * <p>
     * 反射对象获取泛型，如果找不到找父类
     * </p>
     * @param clazz 对象
     * @param index 泛型所在位置
     * @return Class
     */
    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            if (!clazz.getSuperclass().equals(Object.class)) {
                return getSuperClassGenericType(clazz.getSuperclass(), index);
            }
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    /**
     * <p>
     * 获取该类的所有属性列表
     * </p>
     * @param clazz 反射类
     */
    public static Map<String, Field> getFieldMap(Class<?> clazz) {
        List<Field> fieldList = getFieldList(clazz);
        return !fieldList.isEmpty() ? fieldList.stream().collect(Collectors.toMap(Field::getName, field -> field))
                : Collections.emptyMap();
    }

    /**
     * <p>
     * 获取该类的所有属性列表
     * </p>
     * @param clazz 反射类
     */
    public static List<Field> getFieldList(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return Collections.emptyList();
        }
        return computeIfAbsent(CLASS_FIELD_CACHE, clazz, k -> {
            Field[] fields = k.getDeclaredFields();
            List<Field> superFields = new ArrayList<>();
            Class<?> currentClass = k.getSuperclass();
            while (currentClass != null) {
                Field[] declaredFields = currentClass.getDeclaredFields();
                Collections.addAll(superFields, declaredFields);
                currentClass = currentClass.getSuperclass();
            }
            /* 排除重载属性 */
            Map<String, Field> fieldMap = excludeOverrideSuperField(fields, superFields);
            /*
             * 重写父类属性过滤后处理忽略部分，支持过滤父类属性功能 场景：中间表不需要记录创建时间，忽略父类 createTime 公共属性 中间表实体重写父类属性
             * ` private transient Date createTime; `
             */
            return fieldMap.values().stream().toList();
        });
    }

    public static <T> T newInstance(Class<T> clazz) {
        Assert.notNull(clazz, "class must not be null");
        try {
            return clazz.getDeclaredConstructor().newInstance();
        }
        catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new DynamicSqlException("Can't get install from :" + clazz.getName());
        }
    }

    private static <K, V> V computeIfAbsent(Map<K, V> concurrentHashMap, K key,
            Function<? super K, ? extends V> mappingFunction) {
        V v = concurrentHashMap.get(key);
        if (v != null) {
            return v;
        }
        return concurrentHashMap.computeIfAbsent(key, mappingFunction);
    }

    /**
     * <p>
     * 获取该类的所有属性列表
     * </p>
     * @param clazz 反射类
     * @deprecated 3.4.0
     */
    @Deprecated
    public static List<Field> doGetFieldList(Class<?> clazz) {
        if (clazz.getSuperclass() != null) {
            /* 排除重载属性 */
            Map<String, Field> fieldMap = excludeOverrideSuperField(clazz.getDeclaredFields(),
                    /* 处理父类字段 */
                    getFieldList(clazz.getSuperclass()));
            /*
             * 重写父类属性过滤后处理忽略部分，支持过滤父类属性功能 场景：中间表不需要记录创建时间，忽略父类 createTime 公共属性 中间表实体重写父类属性
             * ` private transient Date createTime; `
             */
            return fieldMap.values().stream()
                    /* 过滤静态属性 */
                    .filter(f -> !Modifier.isStatic(f.getModifiers()))
                    /* 过滤 transient关键字修饰的属性 */
                    .filter(f -> !Modifier.isTransient(f.getModifiers())).toList();
        }
        else {
            return Collections.emptyList();
        }
    }

    /**
     * <p>
     * 排序重置父类属性
     * </p>
     * @param fields 子类属性
     * @param superFieldList 父类属性
     */
    public static Map<String, Field> excludeOverrideSuperField(Field[] fields, List<Field> superFieldList) {
        // 子类属性
        Map<String, Field> fieldMap = Stream.of(fields).collect(toMap(Field::getName, identity(), (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        }, LinkedHashMap::new));
        superFieldList.stream().filter(field -> !fieldMap.containsKey(field.getName()))
                .forEach(f -> fieldMap.put(f.getName(), f));
        return fieldMap;
    }

    /**
     * 判断是否为基本类型或基本包装类型
     * @param clazz class
     * @return 是否基本类型或基本包装类型
     */
    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        assert clazz != null;
        return (clazz.isPrimitive() || PRIMITIVE_WRAPPER_TYPE_MAP.containsKey(clazz));
    }

    public static Class<?> resolvePrimitiveIfNecessary(Class<?> clazz) {
        return (clazz.isPrimitive() && clazz != void.class ? PRIMITIVE_TYPE_TO_WRAPPER_MAP.get(clazz) : clazz);
    }

    /**
     * 获得一个类中所有方法列表，包括其父类中的方法
     * @param beanClass 类，非{@code null}
     * @return 方法列表
     * @throws SecurityException 安全检查异常
     */
    public static Method[] getMethods(Class<?> beanClass) throws SecurityException {
        Assert.notNull(beanClass, "bean class can't be null");
        return computeIfAbsent(METHODS_CACHE, beanClass, k -> getMethodsDirectly(k, true, true));
    }

    public static Method[] getMethodsDirectly(Class<?> beanClass, boolean withSupers, boolean withMethodFromObject)
            throws SecurityException {
        // Assert.notNull(beanClass);

        if (beanClass.isInterface()) {
            // 对于接口，直接调用Class.getMethods方法获取所有方法，因为接口都是public方法
            return withSupers ? beanClass.getMethods() : beanClass.getDeclaredMethods();
        }

        final Map<String, Method> result = new HashMap<>();
        Class<?> searchType = beanClass;
        while (searchType != null) {
            if (false == withMethodFromObject && Object.class == searchType) {
                break;
            }

            Arrays.stream(searchType.getDeclaredMethods())
                    .forEach(method -> result.putIfAbsent(getUniqueKey(method), method));
            getDefaultMethodsFromInterface(searchType)
                    .forEach(method -> result.putIfAbsent(getUniqueKey(method), method));

            searchType = (withSupers && !searchType.isInterface()) ? searchType.getSuperclass() : null;
        }

        return result.values().toArray(new Method[0]);
    }

    private static String getUniqueKey(Method method) {
        final StringBuilder sb = new StringBuilder();
        sb.append(method.getReturnType().getName()).append('#');
        sb.append(method.getName());
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            if (i == 0) {
                sb.append(':');
            }
            else {
                sb.append(',');
            }
            sb.append(parameters[i].getName());
        }
        return sb.toString();
    }

    /**
     * 获取类对应接口中的非抽象方法（default方法）
     * @param clazz 类
     * @return 方法列表
     */
    private static List<Method> getDefaultMethodsFromInterface(Class<?> clazz) {
        List<Method> result = new ArrayList<>();
        for (Class<?> ifc : clazz.getInterfaces()) {
            for (Method m : ifc.getMethods()) {
                if (m.getModifiers() == Modifier.ABSTRACT) {
                    result.add(m);
                }
            }
        }
        return result;
    }

}
