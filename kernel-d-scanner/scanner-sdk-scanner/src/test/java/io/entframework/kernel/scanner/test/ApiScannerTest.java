package io.entframework.kernel.scanner.test;

import cn.hutool.core.util.RandomUtil;
import io.entframework.kernel.scanner.api.annotation.ApiResource;
import io.entframework.kernel.scanner.api.annotation.GetResource;
import io.entframework.kernel.scanner.api.annotation.PostResource;
import io.entframework.kernel.scanner.api.factory.ClassMetadataFactory;
import io.entframework.kernel.scanner.api.pojo.resource.FieldMetadata;
import io.entframework.kernel.scanner.api.util.MethodReflectUtil;
import io.entframework.kernel.scanner.test.controller.TestController;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ApiScannerTest extends TestCase {

    @Test
    public void testScanner() {

        Class<?> clazz = TestController.class;
        List<Class<? extends Annotation>> scanAnnotations = Arrays.asList(ApiResource.class, GetResource.class, PostResource.class);

        for (Class<? extends Annotation> annoClass : scanAnnotations) {
            Method[] methods = MethodUtils.getMethodsWithAnnotation(clazz, annoClass);
            for (Method method : methods) {
                Annotation annotation = method.getAnnotation(annoClass);
                if (annotation != null) {
                    Type returnType = MethodReflectUtil.getMethodReturnType(method);
                    String processReturnTypeUuid = RandomUtil.randomString(32);
                    log.info("process method: {}", method.getName());
                    FieldMetadata metadata = ClassMetadataFactory.beginCreateFieldMetadata(returnType, processReturnTypeUuid);
                }
            }
        }
    }
}
