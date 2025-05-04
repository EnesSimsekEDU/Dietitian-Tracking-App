package com.dietitianTrackingApp.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class IndexRedirectFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();

        // Sadece kök URL'yi kontrol et
        if (requestURI.equals(contextPath + "/") || requestURI.equals(contextPath)) {
            httpResponse.sendRedirect(contextPath + "/index");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Temizleme işlemleri
    }
}
