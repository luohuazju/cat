package com.sillycat.corvus.web;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.apache.velocity.Template;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.corvus.helper.VelocityTemplateHelper;
import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.modules.action.ActionLoader;
import com.sillycat.corvus.web.modules.page.PageLoader;
import com.sillycat.corvus.web.rundata.RundataFactory;
import com.sillycat.corvus.web.velocity.VelocityContext;
import com.sillycat.corvus.web.velocity.VelocityContextFactory;

public class DispatcherController extends AbstractDispatcherController {

	private static final long serialVersionUID = 7507090603962465590L;

	protected void deal(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding(ENCODING);
		String servletPath = request.getServletPath();
		Template template = null;
		JspFactory jspFactory = null;
		PageContext pageContext = null;
		long start = System.currentTimeMillis();
		VelocityContext context = null;
		try {
			setContentType(request, response);
			RunData rundata = RundataFactory.getRundata(request, response,
					getServletConfig());
			context = VelocityContextFactory.getContext(rundata, this);
			ActionLoader.getInstance().exec(rundata, context, this);
			PageLoader.getInstance().exec(rundata, context, this);
			template = getTemplate(VelocityTemplateHelper
					.getPageTemplatePath(servletPath),
					ControllerConstant.ENCODING);
			jspFactory = JspFactory.getDefaultFactory();
			pageContext = jspFactory.getPageContext(this, request, response,
					null, true, 8192, true);
			Writer writer = pageContext.getOut();
			template.merge(context, writer);
			writer.flush();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			error(request, response, e);
		} finally {
			long end = System.currentTimeMillis();
			log.info(servletPath + " spend : " + (end - start)
					+ " millisecond ");
			if (jspFactory != null) {
				jspFactory.releasePageContext(pageContext);
			}
			context = null;
		}
	}

}
