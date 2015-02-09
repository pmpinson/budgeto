package org.pmp.budgeto.common.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CorsFilter implements Filter {

    public static final String ALLOW_ALL_ORIGIN = "*";

    private String allowOrigin = ALLOW_ALL_ORIGIN;

    public CorsFilter(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        if (allowOrigin != null && !"".equals(allowOrigin.trim())) {
            response.setHeader("Access-Control-Allow-Origin", allowOrigin);
        }
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(req, res);
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }

}
