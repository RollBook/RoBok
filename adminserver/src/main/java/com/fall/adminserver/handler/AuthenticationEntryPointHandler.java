package com.fall.adminserver.handler;

import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.utils.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author FAll
 * @date 2023/4/14 下午10:14
 * 校验入口处理器
 */
@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    private final ObjectMapper mapper;

    public AuthenticationEntryPointHandler(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        String json = mapper.writeValueAsString(ResponseRecord.fail(HttpServletResponse.SC_UNAUTHORIZED,
                authException.getMessage()) );

        WebUtil.renderString(response,json);

    }
}
