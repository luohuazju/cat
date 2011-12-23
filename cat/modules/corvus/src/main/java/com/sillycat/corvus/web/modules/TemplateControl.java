package com.sillycat.corvus.web.modules;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.runtime.RuntimeSingleton;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.corvus.helper.JavaClassHelper;
import com.sillycat.corvus.helper.VelocityTemplateHelper;
import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.DispatcherController;
import com.sillycat.corvus.web.modules.control.Control;
import com.sillycat.corvus.web.modules.control.ControlLoader;
import com.sillycat.corvus.web.velocity.VelocityContext;

public class TemplateControl {
	private static final Log log = LogFactory.getLog(TemplateControl.class);

	private VelocityContext context = null;

	private String templateName = null;

	private RunData runData = null;

	private DispatcherController servlet = null;

	public TemplateControl(RunData rundata, DispatcherController servlet) {
		context = new VelocityContext();
		this.runData = rundata;
		this.servlet = servlet;
	}

	/**
	 * templateName:admin:aa.vm or feedback/aa.vm admin:aa.vm will find admin
	 * module's control/aa.vm feedback/aa.vm will find current module's
	 * control/feedback/aa.vm
	 * 
	 * @param templateName
	 * @return
	 */
	public TemplateControl setTemplate(String templateName) {
		this.templateName = templateName;
		return this;
	}

	public TemplateControl setParameter(String param, String paramValue) {
		context.put(param, paramValue);
		return this;
	}

	public String toString() {
		String servletPath = runData.getRequest().getServletPath();
		try {
			String controlClassPath = JavaClassHelper.getControlClassName(
					templateName, servletPath);
			Control control = ControlLoader.getInstance().getControl(
					controlClassPath, servlet);
			if (control != null) {
				control.doPerform(runData, context);
			}
			StringWriter stringWriter = new StringWriter();
			Template template = RuntimeSingleton.getTemplate(
					VelocityTemplateHelper.getControlTemplateName(templateName,
							servletPath), ControllerConstant.ENCODING);
			template.merge(context, stringWriter);
			context.clear();
			return stringWriter.toString();
		} catch (Exception e) {
			log.error("serverName:" + e.getMessage(), e);
		}
		return null;
	}
}
