
package com.supinfo.supforum.controller;

import com.supinfo.supforum.entity.User;
import com.supinfo.supforum.entity.UserType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter(filterName="AuthenticateFilter")
public class AuthenticateFilter implements Filter {
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)res;
		UserController userController = (UserController)request.getSession().getAttribute("userController");             

                String uri = request.getRequestURI();
                String path = request.getContextPath();
                if(userController == null || userController.getLoggedUser() == null)
                {
                    response.sendRedirect(path+"/index.xhtml?faces-redirect=true");
                    return;
                }
                if(userController.getLoggedUser().getType() == UserType.User && (uri.contains("moderator") == true || uri.contains("administrator") == true))  
                {
                    userController.setLoggedUser(null);
                    response.sendRedirect(path+"/index.xhtml?faces-redirect=true");
                    return;
                }
                else if(userController.getLoggedUser().getType() == UserType.Moderator && uri.contains("administrator") == true)
                {
                    userController.setLoggedUser(null);
                    response.sendRedirect(path+"/index.xhtml?faces-redirect=true");
                    return;
                }
		else
                {
                    chain.doFilter(req, res);
                    return;
		}
	}
	
	public void init(FilterConfig fConfig) throws ServletException
	{

	}
	
	public void destroy()
	{
		
	}
}