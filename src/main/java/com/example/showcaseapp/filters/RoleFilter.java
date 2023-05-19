//package com.example.showcaseapp.filters;
//
//import com.example.showcaseapp.entity.User;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class RoleFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//        throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//
//        User user = (User) httpRequest.getSession().getAttribute("user");
//        if(user != null) {
//            if (user.hasRole("Admin")) {
//                chain.doFilter(request, response);
//                return;
//            }
//        }
//
//        //httpResponse.sendRedirect("/");
//        chain.doFilter(request, response);
//     }
//}
