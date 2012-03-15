package com.sillycat.corvus.models;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.web.rundata.ParameterParser;

public class RunData {
	private static final String BUTTON = "eventSubmit_";

	private static final int BUTTON_LENGTH = BUTTON.length();

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private ServletContext context;

	private ServletConfig config;

	private Set<?> keySet;

	private String action;

	private String event;

	private ParameterParser parameterParser;

	/**
	 * @param request
	 * @param response
	 * @param session
	 * @param context
	 * @param config
	 */
	public RunData(HttpServletRequest request, HttpServletResponse response,
			HttpSession session, ServletContext context, ServletConfig config) {
		super();
		this.request = request;
		this.response = response;
		this.session = session;
		this.context = context;
		this.config = config;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HttpSession getSession() {
		if (session == null) {
			session = request.getSession();
		}
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public ServletConfig getConfig() {
		return config;
	}

	public void setConfig(ServletConfig config) {
		this.config = config;
	}

	public ServletContext getContext() {
		if (context == null) {
			context = config.getServletContext();
		}
		return context;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public Set<?> getKeySet() {
		if (keySet == null) {
			keySet = this.getParameterParser().keySet();
		}
		return keySet;
	}

	/**
	 * get action name
	 * @return
	 */
	public String getAction() {
		if (StringUtil.isBlank(action)) {
			action = getParameterParser().getString(ControllerConstant.ACTION);
		}
		return action;
	}

	/**
	 * get event name
	 * @return
	 */
	public String getEvent() {
		if (StringUtil.isBlank(event)) {
			String key = null;
			for (Iterator<?> it = this.getKeySet().iterator(); it.hasNext();) {
				key = (String) it.next();
				if (key.startsWith(BUTTON)) {
					if (StringUtil.isNotBlank(getParameterParser().getString(
							key))) {
						event = formatString(key);
						break;
					}
				}
			}
		}
		return event;
	}

	/**
	 * format event name
	 * @param input
	 * @return
	 */
	private final String formatString(String input) {
		String tmp = input;
		if (tmp != null) {
			if (tmp.length() > BUTTON_LENGTH) {
				tmp = tmp.substring(BUTTON_LENGTH, tmp.length());
			}
		}
		return tmp;
	}

	/**
	 * @return Returns the parameterParser.
	 */
	public ParameterParser getParameterParser() {
		return parameterParser;
	}

	/**
	 * @param parameterParser
	 *            The parameterParser to set.
	 */
	public void setParameterParser(ParameterParser parameterParser) {
		this.parameterParser = parameterParser;
	}
}
