package com.bjpow.crm.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 过滤器：处理post乱码
 */
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest rqe, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        System.out.println ("进入到过滤字符编码的过滤器" );
        rqe.setCharacterEncoding ("utf-8");
        resp.setContentType ("text/html;charset=utf-8");
        //将请求放行
        filterChain.doFilter (rqe,resp);

    }

    @Override
    public void destroy() {

    }

}
