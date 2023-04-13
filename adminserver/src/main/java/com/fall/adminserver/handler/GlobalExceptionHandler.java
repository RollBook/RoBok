package com.fall.adminserver.handler;

import com.fall.adminserver.model.vo.ResponseRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author FAll
 * @date 2023/4/13 下午4:52
 * 全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final ObjectMapper mapper;

    public GlobalExceptionHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    /**
     * @param e        异常
     * @param request  http请求
     * @author FAll
     * @description 请求参数缺失
     * @return: com.fall.robok.util.bean.ResponseRecord
     * @date 2022/11/29 15:50
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseRecord<Void> missingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                  HttpServletRequest request) {
        logWarn(request);
        log.warn("请求参数缺失", e);
        return ResponseRecord.fail( HttpServletResponse.SC_BAD_REQUEST,
                String.format("请求参数缺失:%s", e.getParameterName()) );
    }

    /**
     * @param e        异常
     * @param request  http请求
     * @author FAll
     * @description 请求参数类型不匹配
     * @return: com.fall.robok.util.bean.ResponseRecord
     * @date 2022/11/29 15:53
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseRecord<Void> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,
                                                       HttpServletRequest request) {
        logWarn(request);
        log.warn("参数类型不匹配", e);
        return ResponseRecord.fail(HttpServletResponse.SC_BAD_REQUEST,
                String.format("参数类型不匹配:%s", e.getMessage()) );
    }

    /**
     * @param e        异常
     * @param request  http请求
     * @author FAll
     * @description 参数校验错误
     * @return: com.fall.robok.util.bean.ResponseRecord
     * @date 2022/11/29 16:01
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseRecord<Void> methodArgumentNotValidException(MethodArgumentNotValidException e,
                                                   HttpServletRequest request) {
        logWarn(request);
        log.warn("参数校验错误", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        return ResponseRecord.fail(HttpServletResponse.SC_BAD_REQUEST,
                String.format("参数校验错误:%s", Objects.requireNonNull(fieldError).getDefaultMessage()) );
    }

    /**
     * @author FAll
     * @description
     * @param e         异常
     * @param request   http请求
     * @return: com.fall.robok.util.bean.ResponseRecord
     * @date 2023/2/25 22:01
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseRecord<Void> constraintViolationException(ConstraintViolationException e,
                                                HttpServletRequest request) {
        logWarn(request);
        log.warn("约束错误",e);
        return ResponseRecord.fail(HttpServletResponse.SC_BAD_REQUEST,
                String.format("约束错误:%s",e.getMessage()) );
    }

    /**
     * @param e 异常
     * @author FAll
     * @description 请求地址不存在 (已弃用)
     * @return: com.fall.robok.util.bean.ResponseRecord
     * @date 2022/11/29 16:20
     */
    @Deprecated
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseRecord<Void> noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        log.warn("请求地址不存在", e);
        return ResponseRecord.fail(HttpServletResponse.SC_BAD_REQUEST,
                String.format("请求地址 %s 不存在", e.getRequestURL()));
    }

    /**
     * @param e        异常
     * @param request  http请求
     * @author FAll
     * @description 请求方式错误
     * @return: com.fall.robok.util.bean.ResponseRecord
     * @date 2022/11/29 16:41
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseRecord<Void> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e,
                                                                 HttpServletRequest request) {
        logWarn(request);
        log.warn("请求方式错误", e);
        return ResponseRecord.fail(HttpServletResponse.SC_BAD_REQUEST,
                String.format("请求方法不正确:%s", e.getMessage()));
    }


    /**
     * @param e        异常
     * @param request  http请求
     * @author FAll
     * @description 兜底捕获其它异常
     * @return: com.fall.robok.util.bean.ResponseRecord
     * @date 2022/11/29 16:58
     */
    @ExceptionHandler(Exception.class)
    public ResponseRecord<Void> exceptionHandler(Exception e,
                                    HttpServletRequest request) {
        logError(request);
        log.error(" 内部错误: {}", e.getMessage(), e);
        return ResponseRecord.fail(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"内部错误");
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
                headerMap.put(headerName, request.getHeader(headerName)+"\n");
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
                headerMap.put(headerName, request.getHeader(headerName)+"\n");
            }
            log.error(" 请求 头: {}", mapper.writeValueAsString(headerMap));
            log.error(" 请求 参数: {}", mapper.writeValueAsString(request.getParameterMap()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
