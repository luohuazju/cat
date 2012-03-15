package com.sillycat.corvus.helper;

import com.sillycat.aries.constants.PageConstant;
import com.sillycat.aries.utils.StringUtil;

public class JavaClassHelper {

	public static String getActionClassName(String actionName,
			String servletPath) {
		if (servletPath.startsWith(PageConstant.URL_SPLIT)) {
			servletPath = servletPath.substring(1, servletPath
					.lastIndexOf(PageConstant.DOT));
		} else {
			servletPath = servletPath.substring(0, servletPath
					.lastIndexOf(PageConstant.DOT));
		}

		String[] paths = StringUtil.split(servletPath, PageConstant.URL_SPLIT);
		String returnValue = "";
		if (paths != null && paths.length > 2) {
			String storeName = paths[0];
			String language = paths[1];

			int endIndex = actionName.lastIndexOf(PageConstant.URL_SPLIT);
			String packageName = actionName.substring(0, endIndex);
			String className = actionName.substring(endIndex + 1, actionName
					.length());
			className = StringUtil.capitalize(className);
			returnValue = storeName + PageConstant.DOT + language
					+ PageConstant.DOT + PageConstant.DEFAULT_ACTION
					+ PageConstant.DOT + packageName + PageConstant.DOT
					+ className;
		}
		return returnValue;
	}

	public static String getControlClassName(String controlName,
			String servletPath) {
		if (servletPath.startsWith(PageConstant.URL_SPLIT)) {
			servletPath = servletPath.substring(1, servletPath
					.lastIndexOf(PageConstant.DOT));
		} else {
			servletPath = servletPath.substring(0, servletPath
					.lastIndexOf(PageConstant.DOT));
		}

		if (controlName.startsWith(PageConstant.URL_SPLIT)) {
			controlName = controlName.substring(1, controlName
					.lastIndexOf(PageConstant.DOT));
		} else {
			controlName = controlName.substring(0, controlName
					.lastIndexOf(PageConstant.DOT));
		}

		String[] paths = StringUtil.split(servletPath, PageConstant.URL_SPLIT);
		String returnValue = "";
		if (paths != null && paths.length > 2) {
			String storeName = paths[0];
			String language = paths[1];
			StringBuffer sb = new StringBuffer();
			for (int i = 2; i < paths.length - 1; i++) {
				sb.append(paths[i]).append(PageConstant.DOT);
			}
			String packagePath = sb.toString();
			packagePath = packagePath.substring(0, packagePath.length() - 1);
			// the difference between controlName
			controlName = StringUtil.capitalize(controlName);
			returnValue = storeName + PageConstant.DOT + language
					+ PageConstant.DOT + PageConstant.DEFAULT_CONTROL
					+ PageConstant.DOT + packagePath + PageConstant.DOT
					+ controlName;
		}
		return returnValue;
	}

	public static String getScreenClassName(String servletPath) {
		if (servletPath.startsWith(PageConstant.URL_SPLIT)) {
			servletPath = servletPath.substring(1, servletPath
					.lastIndexOf(PageConstant.DOT));
		} else {
			servletPath = servletPath.substring(0, servletPath
					.lastIndexOf(PageConstant.DOT));
		}

		String[] paths = StringUtil.split(servletPath, PageConstant.URL_SPLIT);
		String returnValue = "";
		if (paths != null && paths.length > 2) {
			String storeName = paths[0];
			String language = paths[1];
			StringBuffer sb = new StringBuffer();
			for (int i = 2; i < paths.length - 1; i++) {
				sb.append(paths[i]).append(PageConstant.DOT);
			}
			String packagePath = sb.toString();
			packagePath = packagePath.substring(0, packagePath.length() - 1);
			String className = paths[paths.length - 1];
			// the difference between screenName
			className = StringUtil.capitalize(className);
			returnValue = storeName + PageConstant.DOT + language
					+ PageConstant.DOT + PageConstant.DEFAULT_SCREEN
					+ PageConstant.DOT + packagePath + PageConstant.DOT
					+ className;
		}
		return returnValue;
	}

}
