package com.xihongshi.expert.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xihongshi.expert.common.Result;
import com.xihongshi.expert.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            new ObjectMapper().writeValue(response.getWriter(), Result.error(401, "未登录或token已过期"));
            return false;
        }

        String token = authHeader.substring(7);
        try {
            if (jwtUtil.isTokenExpired(token)) {
                response.setContentType("application/json;charset=UTF-8");
                response.setStatus(401);
                new ObjectMapper().writeValue(response.getWriter(), Result.error(401, "token已过期，请重新登录"));
                return false;
            }
            request.setAttribute("userId", jwtUtil.getUserIdFromToken(token));
            request.setAttribute("role", jwtUtil.getRoleFromToken(token));
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(401);
            new ObjectMapper().writeValue(response.getWriter(), Result.error(401, "token无效"));
            return false;
        }
        return true;
    }
}
