package com.sillycat.corvus.web.modules.page;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.runtime.RuntimeSingleton;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.aries.exceptions.TemplateNoFoundException;
import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.helper.JavaClassHelper;
import com.sillycat.corvus.helper.VelocityTemplateHelper;
import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.DispatcherController;
import com.sillycat.corvus.web.modules.GenericLoader;
import com.sillycat.corvus.web.modules.screen.ScreenLoader;
import com.sillycat.corvus.web.velocity.VelocityContext;

public class PageLoader extends GenericLoader {

	private static final long serialVersionUID = -4197234394034652260L;

	private static Log log = LogFactory.getLog(PageLoader.class);

	private static PageLoader instance = new PageLoader(50);

	private PageLoader() {
		super();
	}

	private PageLoader(int i) {
		super(i);
	}

	public void exec(RunData rundata, VelocityContext context,
			DispatcherController servlet) throws Exception {
		String screenServlet = rundata.getRequest().getServletPath();
		String screenClass = screenServlet;
		String screenTemplate = screenServlet;
		Template template = null;
		if (StringUtil.isNotBlank(screenClass)) {
			screenTemplate = VelocityTemplateHelper
					.getScreenTemplatePath(screenTemplate);
			if (StringUtil.isNotBlank(screenTemplate)) {
				// exist the screen template
				screenClass = JavaClassHelper.getScreenClassName(screenClass);
				ScreenLoader.getInstance().getScreen(screenClass, servlet)
						.doPerform(rundata, context);
				template = RuntimeSingleton.getTemplate(screenTemplate,
						ControllerConstant.ENCODING);
				StringWriter stringWriter = new StringWriter();
				template.merge(context, stringWriter);
				context.put(ControllerConstant.SCREEN_PLACEHOLDER, stringWriter
						.toString());
			} else {
				log.error("not found template " + screenTemplate);
				throw new TemplateNoFoundException("NOT FOUND TEMPLATE "
						+ screenTemplate);
			}
		}
	}

	public static PageLoader getInstance() {
		return instance;
	}
}
