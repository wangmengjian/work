package com.nankingdata.yc.work.filter;

import com.nankingdata.yc.common.Users;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName="authFilter", urlPatterns={"/*"})
@Component
public class AuthFilter implements Filter {

    @Value("${auth.openUrl}")
    private String openUrl;

    @Override
    public void init(FilterConfig args) throws ServletException {}
    private void sentMsg(ServletResponse res, String code, String msg) throws IOException{
        res.setContentType("application/json");
        PrintWriter writer = res.getWriter();
        writer.write("{\n" +
                "    \"status\": {\n" +
                "        \"code\": "+code+",\n" +
                "        \"message\": \""+msg+"\"\n" +
                "    },\n" +
                "    \"result\": null\n" +
                "}");
        writer.close();
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        String url = req.getRequestURI();

        Users user=new Users();
        user.setId(1);
        user.setDepartmentId(1);
        session.setAttribute("user",user);

        Users users= (Users) session.getAttribute("user");
        if(url.startsWith("/work")&&users==null){
            sentMsg(res,"-2","用户未登录");
            return;
        }else{
            chain.doFilter(request,response);
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

