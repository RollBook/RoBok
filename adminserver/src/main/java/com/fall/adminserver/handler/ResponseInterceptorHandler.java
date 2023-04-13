package com.fall.adminserver.handler;

import com.fall.adminserver.model.vo.ResponseRecord;
import io.micrometer.common.lang.NonNullApi;
import jakarta.annotation.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author FAll
 * @date 2023/4/13 下午4:53
 * 响应拦截处理器
 */
@NonNullApi
@RestControllerAdvice
public class ResponseInterceptorHandler implements ResponseBodyAdvice<Object> {

    /**
     * @author FAll
     * @description 判断拦截哪些响应
     * @param returnType 返回类型
     * @param converterType 转换类型
     * @return: boolean
     * @date 2023/4/13 下午5:54
     */
    @Override
    public boolean supports( MethodParameter returnType,
                             Class<? extends HttpMessageConverter<?>> converterType) {
        // 拦截所有响应
        return true;
    }

    /**
     * @author FAll
     * @description 拦截处理
     * @param body 响应体
     * @param returnType 返回类型
     * @param selectedContentType 内容类型
     * @param selectedConverterType 转换类型
     * @param request http请求
     * @param response http响应
     * @return: java.lang.Object
     * @date 2023/4/13 下午5:55
     */
    @Override
    public Object beforeBodyWrite(@Nullable Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {


        // 根据响应body中的status设定http响应码
        if(body instanceof ResponseRecord<?> responseRecord) {
            response.setStatusCode(HttpStatus.valueOf(responseRecord.status()));
        }
        return body;
    }

}
