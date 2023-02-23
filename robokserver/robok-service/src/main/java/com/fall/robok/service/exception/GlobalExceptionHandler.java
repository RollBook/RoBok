package com.fall.robok.service.exception;

import com.fall.robok.util.bean.ResBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author FAll
 * @date 2022/11/29 13:50
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final ObjectMapper mapper;

    @Autowired
    public GlobalExceptionHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * @param e        异常
     * @param request  http请求
     * @param response http响应
     * @author FAll
     * @description 请求参数缺失
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 15:50
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResBean missingServletRequestParameterException(MissingServletRequestParameterException e,
                                                           HttpServletRequest request,
                                                           HttpServletResponse response) {
        logWarn(request);
        log.warn("请求参数缺失", e);
        response.setStatus(405);
        return ResBean.badRequest(String.format("请求参数缺失:%s", e.getParameterName()));
    }

    /**
     * @param e        异常
     * @param request  http请求
     * @param response http响应
     * @author FAll
     * @description 请求参数类型不匹配
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 15:53
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResBean methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
        logWarn(request);
        log.warn("参数类型不匹配", e);
        response.setStatus(405);
        return ResBean.badRequest(String.format("参数类型不匹配:%s", e.getMessage()));
    }

    /**
     * @param e        异常
     * @param response http响应
     * @param request  http请求
     * @author FAll
     * @description 参数校验错误
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 16:01
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResBean methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        logWarn(request);
        log.warn("参数校验错误", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        response.setStatus(405);
        return ResBean.badRequest(String.format("参数校验错误:%s", Objects.requireNonNull(fieldError).getDefaultMessage()));
    }

    /**
     * @param e 异常
     * @author FAll
     * @description 请求地址不存在 (已弃用)
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 16:20
     */
    @Deprecated
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResBean noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        log.warn("请求地址不存在", e);
        return ResBean.badRequest(404, String.format("请求地址 %s 不存在", e.getRequestURL()));
    }

    /**
     * @param e        异常
     * @param request  http请求
     * @param response http响应
     * @author FAll
     * @description 请求方式错误
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 16:41
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResBean httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e,
                                                                 HttpServletRequest request,
                                                                 HttpServletResponse response) {
        logWarn(request);
        log.warn("请求方式错误", e);
        response.setStatus(405);
        return ResBean.badRequest(405, String.format("请求方法不正确:%s", e.getMessage()));
    }


    /**
     * @param e        异常
     * @param response http响应
     * @param request  http请求
     * @author FAll
     * @description 兜底捕获其它异常
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 16:58
     */
    @ExceptionHandler(Exception.class)
    public ResBean exceptionHandler(Exception e,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        logError(request);
        log.error(" 内部错误: {}", e.getMessage(), e);
        response.setStatus(500);
        return ResBean.internalError("内部错误");
    }

    /**
     * @param request http请求
     * @author FAll
     * @description 输出告警接口
     * @date 2023/2/23 14:42
     */
    private void logWarn(HttpServletRequest request) {
        try {
            log.warn(" 请求 URL: {}", request.getRequestURL());
            log.warn(" 请求 方式: {}", request.getMethod());
            log.warn(" 请求 IP: {}", request.getRemoteAddr());
            Enumeration<String> headerNames = request.getHeaderNames();
            HashMap<String, String> headerMap = new HashMap<>();
            String headerName;
            while (headerNames.hasMoreElements()) {
                headerName = headerNames.nextElement();
                headerMap.put(headerName, request.getHeader(headerName));
            }
            log.warn(" 请求 头: {}", mapper.writeValueAsString(headerMap));
            log.warn(" 请求 参数: {}", mapper.writeValueAsString(request.getParameterMap()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param request http请求
     * @author FAll
     * @description 输出报错接口
     * @date 2023/2/23 14:41
     */
    private void logError(HttpServletRequest request) {
        try {
            log.error(" 请求 URL: {}", request.getRequestURL());
            log.error(" 请求 方式: {}", request.getMethod());
            log.error(" 请求 IP: {}", request.getRemoteAddr());
            Enumeration<String> headerNames = request.getHeaderNames();
            HashMap<String, String> headerMap = new HashMap<>();
            String headerName;
            while (headerNames.hasMoreElements()) {
                headerName = headerNames.nextElement();
                headerMap.put(headerName, request.getHeader(headerName));
            }
            log.error(" 请求 头: {}", mapper.writeValueAsString(headerMap));
            log.error(" 请求 参数: {}", mapper.writeValueAsString(request.getParameterMap()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
