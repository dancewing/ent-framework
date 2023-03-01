package io.entframework.kernel.db.mybatis.binding;

import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapperProxyFactoryExt<T> {

    private final Class<T> mapperInterface;

    private final Map<String, MapperProxyExt.MapperMethodInvoker> methodCache = new ConcurrentHashMap<>();

    public MapperProxyFactoryExt(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public Map<String, MapperProxyExt.MapperMethodInvoker> getMethodCache() {
        return methodCache;
    }

    @SuppressWarnings("unchecked")
    protected T newInstance(MapperProxyExt<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[] { mapperInterface },
                mapperProxy);
    }

    public T newInstance(SqlSession sqlSession) {
        final MapperProxyExt<T> mapperProxy = new MapperProxyExt<>(sqlSession, mapperInterface, methodCache);
        return newInstance(mapperProxy);
    }

}
