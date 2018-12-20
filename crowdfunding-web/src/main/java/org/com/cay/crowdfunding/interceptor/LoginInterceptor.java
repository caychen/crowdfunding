package org.com.cay.crowdfunding.interceptor;

import org.com.cay.crowdfunding.constant.Constants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.interceptor.LoginInterceptor
 * Date:         2018/12/20
 * Version:      v1.0
 * Desc:
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if(session.getAttribute(Constants.LOGIN_USERNAME) != null){
			return true;
		}
		String path = session.getServletContext().getContextPath();
		response.sendRedirect(path + Constants.LOGIN_PAGE);
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
