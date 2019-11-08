package com.hbsi.shopping.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 登录拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Object user = request.getSession().getAttribute("user");
		if(user != null) {
			return true;
		}else {
			request.getRequestDispatcher("/shopping-mall/login.html").forward(request, response);
			return false;
		}
		
	}

}
