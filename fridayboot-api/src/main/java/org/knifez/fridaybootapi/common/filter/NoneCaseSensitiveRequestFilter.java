package org.knifez.fridaybootapi.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class NoneCaseSensitiveRequestFilter implements Filter {

    /**
     * do过滤器
     *
     * @param servletRequest  servlet请求
     * @param servletResponse servlet响应
     * @param filterChain     过滤链
     * @throws IOException      IOException
     * @throws ServletException servlet异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 接口转小写
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String originalRequestURI = request.getRequestURI();
        String lowerCaseRequestURI = originalRequestURI.toLowerCase();
        // 使用HttpServletRequestWrapper来包装请求，并重写getRequestURI方法
        HttpServletRequestWrapper lowerCaseRequest = new HttpServletRequestWrapper(request) {
            @Override
            public String getRequestURI() {
                return lowerCaseRequestURI;
            }
        };

        filterChain.doFilter(lowerCaseRequest, servletResponse);
    }
}
