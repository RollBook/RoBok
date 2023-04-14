package com.fall.adminserver.handler;

import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.utils.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author FAll
 * @date 2023/4/14 下午10:03
 * 请求拒绝处理器
 */
@Component
public class RequestDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper mapper;

    public RequestDeniedHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {

        String json = mapper.writeValueAsString(ResponseRecord.fail(response.SC_UNAUTHORIZED,"权限不足"));
        WebUtil.renderString(response,json);

    }

}
