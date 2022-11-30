package com.fall.robok.service.exception;

import com.fall.robok.util.bean.ResBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author FAll
 * @date 2022/11/29 13:50
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * @param e
     * @param response
     * @author FAll
     * @description 请求参数缺失
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 15:50
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResBean missingServletRequestParameterException(MissingServletRequestParameterException e,HttpServletResponse response) {
        log.warn("请求参数缺失", e);
        response.setStatus(405);
        return ResBean.badRequest(String.format("请求参数缺失:%s", e.getParameterName()));
    }

    /**
     * @param e
     * @param response
     * @author FAll
     * @description 请求参数类型不匹配
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 15:53
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResBean methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e,HttpServletResponse response) {
        log.warn("参数类型不匹配", e);
        response.setStatus(405);
        return ResBean.badRequest(String.format("参数类型不匹配:%s", e.getMessage()));
    }

    /**
     * @param e
     * @param response
     * @author FAll
     * @description 参数校验错误
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 16:01
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResBean methodArgumentNotValidException(MethodArgumentNotValidException e,HttpServletResponse response) {
        log.warn("参数校验错误", e);
        FieldError fieldError = e.getBindingResult().getFieldError();
        response.setStatus(405);
        return ResBean.badRequest(String.format("参数校验错误:%s", fieldError.getDefaultMessage()));
    }

    /**
     * @param e
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
     * @param e
     * @param response
     * @author FAll
     * @description 请求方式错误
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 16:41
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResBean httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e, HttpServletResponse response) {
        log.warn("请求方式错误", e);
        response.setStatus(405);
        return ResBean.badRequest(405, String.format("请求方法不正确:%s", e.getMessage()));
    }

    /**
     * @param e
     * @param response
     * @author FAll
     * @description 兜底捕获其它异常
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/11/29 16:58
     */
    @ExceptionHandler(Exception.class)
    public ResBean exceptionHandler(Exception e,HttpServletResponse response) {
        log.error(" 内部错误: {}", e.getMessage(), e);
        response.setStatus(500);
        return ResBean.internalError("内部错误");
    }

}
