package com.addressbook.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String path = request.getRequestURI().substring(
                request.getContextPath().length());

        // 放行OPTIONS预检请求（跨域需要）
        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }

        // 放行不需要登录的路径
        if (path.equals("/") || path.equals("/api/login")
                || path.equals("/api/register") || path.equals("/api/me")
                || path.startsWith("/css/")) {
            return true;
        }

        // API请求 — 检查是否已登录
        if (path.startsWith("/api/")) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\"code\":401,\"message\":\"未登录\",\"data\":null}");
                return false;
            }
        }

        return true;
    }
}
