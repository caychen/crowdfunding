package org.com.cay.crowdfunding.interceptor;

import org.com.cay.crowdfunding.constant.Constants;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.interceptor.AuthInterceptor
 * Date:         2018/12/20
 * Version:      v1.0
 * Desc:
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();

		HttpSession session = request.getSession();
		List<String> permissionPathList = (List<String>) session.getAttribute(Constants.PERMISSION_PATH_NAME);
		for (String permissionPath : permissionPathList) {
			if (uri.startsWith(permissionPath)) {
				return true;
			}
		}

		String path = session.getServletContext().getContextPath();
		response.sendRedirect(path + Constants.AUTH_INVALID_PAGE);
		return false;
	}
}
