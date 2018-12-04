package org.com.cay.crowdfunding.web;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Author:       Caychen
 * Class:        org.com.cay.crowdfunding.web.ServerStartupListener
 * Date:         2018/12/3
 * Version:      v1.0
 * Desc:        应用启动监听器
 */
@Slf4j
public class ServerStartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//将web应用名称（名称）保存到application范围中
		ServletContext application = sce.getServletContext();
		String path = application.getContextPath();

		log.info("获取应用根目录：{}", path);
		application.setAttribute("ctx", path);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
