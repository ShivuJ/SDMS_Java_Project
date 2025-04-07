package com.sdmsproject.sdms.Interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Skip authentication check for login and index pages
    	
        if (request.getRequestURI().equals("/") || request.getRequestURI().equals("/login") || request.getRequestURI().equals("/logout")) {
            return true;
        }

        // Check if the request has valid cookies
        if (!hasValidCookies(request)) {
            response.sendRedirect("/"); // Redirect to index.html
            return false; // Stop further processing
        }

        return true; // Allow request to proceed
    }

    private boolean hasValidCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            boolean hasUsername = false;
            boolean hasRole = false;

            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    hasUsername = true;
                }
                if ("userRole".equals(cookie.getName())) {
                    hasRole = true;
                }
            }
            return hasUsername && hasRole; // Ensure both cookies exist
        }
        return false;
    }
}
