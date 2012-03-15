package com.sillycat.corvus.web.modules.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.DispatcherController;
import com.sillycat.corvus.web.velocity.VelocityContext;

public class BaseAction extends ActionEvent {

	private static Log log = LogFactory.getLog(BaseAction.class);

	private ApplicationContext applicationContext;

	public void setServlet(DispatcherController actionServlet) {
		if (actionServlet != null) {

			applicationContext = WebApplicationContextUtils
					.getRequiredWebApplicationContext(actionServlet
							.getServletContext());
			log.info("ApplicationContext=" + applicationContext);
		}
	}

	protected Object getBean(String beanName) {
		if (applicationContext == null) {
			log.error("ApplicationContext is null!!!!");
		}
		return applicationContext.getBean(beanName);
	}

	public void doPerform(RunData data, VelocityContext context)
			throws Exception {
	}

}
