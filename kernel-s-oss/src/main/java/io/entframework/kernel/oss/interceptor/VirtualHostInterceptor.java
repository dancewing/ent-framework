package io.entframework.kernel.oss.interceptor;

import io.entframework.kernel.file.minio.MinIoFileOperator;
import io.entframework.kernel.oss.service.BucketHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class VirtualHostInterceptor implements HandlerInterceptor {

    @Value("${kernel.oss.domain}")
    private String ossDomain;

    @Resource
    private MinIoFileOperator minIoFileOperator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String host = request.getServerName();
        if (!StringUtils.contains(host, ossDomain)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
        String bucket = StringUtils.substringBefore(host, "." + ossDomain);
        if (StringUtils.isEmpty(bucket)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
        try {
            boolean bucketExist = minIoFileOperator.doesBucketExist(bucket);
            if (bucketExist) {
                BucketHolder.set(bucket);
            }
            else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Bucket 不存在");
            }
        }
        catch (Exception ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        BucketHolder.remove();
    }

}
