package com.nankingdata.yc.work.filter;

import com.nankingdata.yc.work.common.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName="authFilter", urlPatterns={"/*"})
@Component
public class AuthFilter implements Filter {

    @Value("${auth.openUrl}")
    private String openUrl;

    @Override
    public void init(FilterConfig args) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        /*HttpSession session = req.getSession();
        Users users =new Users();
        users.setId(1);
        users.setRealName("wmj");
        session.setAttribute("user", users);*/
        String url = req.getRequestURI();
        if (url.startsWith("/api")) {
            filterChain.doFilter(request, response);
        } else{
            filterChain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }

    public String[] getOpenUrl() {
        if(this.openUrl == null || this.openUrl.isEmpty()) {
            return null;
        }else{
            return this.openUrl.split(";");
        }
    }
}

