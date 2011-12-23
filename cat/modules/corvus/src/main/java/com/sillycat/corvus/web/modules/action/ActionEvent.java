package com.sillycat.corvus.web.modules.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.helper.JavaClassHelper;
import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.velocity.VelocityContext;

public abstract class ActionEvent extends Action {
	protected Log log = LogFactory.getLog(this.getClass());

	private static final Class<?>[] methodParams = new Class[] { RunData.class,
			VelocityContext.class };

	private static Map<String, Object> classCache = Collections
			.synchronizedMap(new HashMap<String, Object>());

	public ActionEvent() {
		super();
	}

	public void execute(RunData rundata, VelocityContext context)
			throws Exception {
		try {
			executeEvents(rundata, context);
		} catch (NoSuchMethodException e) {
			throw new Exception("无此方法", e);
		}
	}

	private void executeEvents(RunData rundata, VelocityContext context)
			throws Exception {
		String theButton = rundata.getEvent();
		String actionClass = rundata.getAction();

		String servletPath = rundata.getRequest().getServletPath();

		actionClass = JavaClassHelper.getActionClassName(actionClass,
				servletPath);

		actionClass = actionClass + theButton;

		if (StringUtil.isBlank(theButton) || StringUtil.isBlank(actionClass)) {
			throw new NoSuchMethodException("ActionEvent: The button was null");
		}
		Method method = null;
		try {
			method = (Method) classCache.get(actionClass);
			if (method == null) {
				method = getClass().getMethod(theButton, methodParams);
				classCache.put(actionClass, method);
				log.info("cache the method " + method);
			}
			Object[] methodArgs = new Object[] { rundata, context };
			if (log.isDebugEnabled()) {
				log.debug("Invoking " + method);
			}
			method.invoke(this, methodArgs);
		} catch (InvocationTargetException ite) {
			Throwable t = ite.getTargetException();
			log.error("Invokation of " + method, t);
		} finally {
			;
		}
	}
}
