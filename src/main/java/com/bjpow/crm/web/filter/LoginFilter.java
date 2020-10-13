package com.bjpow.crm.web.filter;

import com.bjpow.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        System.out.println ("进入到验证有没有登陆过的过滤器" );
        HttpServletRequest request1= (HttpServletRequest) request;
        HttpServletResponse response1= (HttpServletResponse) response;
        String path=request1.getServletPath ();
        //不应该拦截的请求自动放行
        if ("/login.jsp".equals (path)||"/settings/user/login.do".equals (path)){
            filterChain.doFilter (request,response);
            //其他资源必须验证有没有登陆过
        }else {

            HttpSession session=request1.getSession ();
            User user= (User) session.getAttribute ("user");
            //如果user为null说明登陆过
            if (user!=null){
                filterChain.doFilter (request,response);
                //没有登陆过
            }else {
            /*
                重定向到登陆页
                在实际项目开发中，对于路径的使用，无论操作是前端还是后端你全部使用绝对路径
                关于重定向的的写法：
                    转发：使用的是一种特殊的绝对路径的使用方式，这种路径前面不加/项目名，这种路径也被称为内部路径
                    重定向：它所使用的是传统的路径的写法，前面项目名以/项目名开头，后面根具体的路径
                这里为什么使用重定向，不使用转发：
                    转发之后，路径会停留在来路径上，而不是跳转到最新资源的路径
                    我们应该在为用户跳转到登录页的同时，将浏览器的地址栏应该自动设置到当前的登录页的路径
            */
                response1.sendRedirect (request1.getContextPath ()+"/login.jsp");
            }

        }


    }

    @Override
    public void destroy() {

    }
}
