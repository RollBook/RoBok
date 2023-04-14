package com.fall.adminserver.utils;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * @author FAll
 * @date 2023/4/14 下午10:00
 * Web工具类
 */
public class WebUtil {
    public static void renderString(HttpServletResponse response, String json) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
