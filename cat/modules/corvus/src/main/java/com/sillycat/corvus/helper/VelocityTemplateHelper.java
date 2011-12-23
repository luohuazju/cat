package com.sillycat.corvus.helper;

import java.net.URL;

import org.apache.velocity.runtime.RuntimeSingleton;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.aries.constants.PageConstant;
import com.sillycat.aries.utils.StringUtil;

public class VelocityTemplateHelper {

	public static void init() {
		URL url = VelocityTemplateHelper.class
				.getResource("/velocity.properties");
		try {
			RuntimeSingleton.init(url.getPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getPageTemplatePath(String vmPage, String fileName) {
		String tmpPage = vmPage + PageConstant.URL_SPLIT + fileName
				+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
		if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
			return tmpPage;
		}
		tmpPage = vmPage + PageConstant.URL_SPLIT + PageConstant.DEFAULT_PAGE
				+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
		if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
			return tmpPage;
		}

		String[] paths = StringUtil.split(vmPage, PageConstant.URL_SPLIT);
		for (int i = 1; i < paths.length + 1; i++) {
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < paths.length - i; j++) {
				sb.append(PageConstant.URL_SPLIT).append(paths[j]);
			}
			tmpPage = vmPage + sb.toString() + fileName
					+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
			// find the page in the right package with right name
			if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
				return tmpPage;
			}
			tmpPage = vmPage + sb.toString() + PageConstant.URL_SPLIT
					+ PageConstant.DEFAULT_PAGE
					+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
			// find the page in the right package with default name
			if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
				return tmpPage;
			}
		}
		return null;
	}

	public static String getPageTemplatePath(String servletPath) {
		// erase the suffix
		if (servletPath.startsWith(PageConstant.URL_SPLIT)) {
			servletPath = servletPath.substring(1, servletPath
					.lastIndexOf(PageConstant.DOT));
		} else {
			servletPath = servletPath.substring(0, servletPath
					.lastIndexOf(PageConstant.DOT));
		}

		String[] names = StringUtil.split(servletPath, PageConstant.URL_SPLIT);

		if (names != null && names.length > 2) {
			String storeName = names[0];
			String language = names[1];
			StringBuffer sb = new StringBuffer();
			for (int i = 2; i < names.length - 1; i++) {
				sb.append(names[i]).append(PageConstant.URL_SPLIT);
			}
			String fullPath = sb.toString();
			fullPath = fullPath.substring(0, fullPath.length() - 1);

			String fileName = names[names.length - 1];

			String pathName = storeName + PageConstant.URL_SPLIT + language
					+ PageConstant.DEFAULT_PAGE_TEMPLATE_PATH
					+ PageConstant.URL_SPLIT + fullPath;

			String tmpPage = getPageTemplatePath(pathName, fileName);
			if (StringUtil.isNotBlank(tmpPage)) {
				return tmpPage;
			}

			if (!PageConstant.DEFAULT_TEMPLATE_LANGUAGE.equals(language)) {
				pathName = storeName + PageConstant.URL_SPLIT
						+ PageConstant.DEFAULT_TEMPLATE_LANGUAGE
						+ PageConstant.DEFAULT_PAGE_TEMPLATE_PATH
						+ PageConstant.URL_SPLIT + fullPath;
				// find the page in the right store but default language
				tmpPage = getPageTemplatePath(pathName, fileName);
				if (StringUtil.isNotBlank(tmpPage)) {
					return tmpPage;
				}
			}

			if (!PageConstant.DEFAULT_TEMPLATE_STORE.equals(storeName)) {
				pathName = PageConstant.DEFAULT_TEMPLATE_STORE
						+ PageConstant.URL_SPLIT + language
						+ PageConstant.DEFAULT_PAGE_TEMPLATE_PATH
						+ PageConstant.URL_SPLIT + fullPath;
				// find the page in the default store with right language
				tmpPage = getPageTemplatePath(pathName, fileName);
				if (StringUtil.isNotBlank(tmpPage)) {
					return tmpPage;
				}
				if (!PageConstant.DEFAULT_TEMPLATE_LANGUAGE.equals(language)) {
					pathName = PageConstant.DEFAULT_TEMPLATE_STORE
							+ PageConstant.URL_SPLIT
							+ PageConstant.DEFAULT_TEMPLATE_LANGUAGE
							+ PageConstant.DEFAULT_PAGE_TEMPLATE_PATH
							+ PageConstant.URL_SPLIT + fullPath;
					// find the screen in the default store but default language
					tmpPage = getPageTemplatePath(pathName, fileName);
					if (StringUtil.isNotBlank(tmpPage)) {
						return tmpPage;
					}
				}
			}
		}
		return null;
	}

	public static String getControlTemplateName(String controlName,
			String servletPath) {
		// erase the suffix
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

		String[] names = StringUtil.split(servletPath, PageConstant.URL_SPLIT);

		if (names != null && names.length > 2) {
			String storeName = names[0];
			String language = names[1];
			StringBuffer sb = new StringBuffer();
			for (int i = 2; i < names.length - 1; i++) {
				sb.append(names[i]).append(PageConstant.URL_SPLIT);
			}
			sb.append(controlName);
			String fullPath = sb.toString();

			String tmpPage = storeName + PageConstant.URL_SPLIT + language
					+ PageConstant.DEFAULT_CONTROL_TEMPLATE_PATH
					+ PageConstant.URL_SPLIT + fullPath
					+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
			// find the control in the right store and language
			if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
				return tmpPage;
			}

			if (!PageConstant.DEFAULT_TEMPLATE_LANGUAGE.equals(language)) {
				tmpPage = storeName + PageConstant.URL_SPLIT
						+ PageConstant.DEFAULT_TEMPLATE_LANGUAGE
						+ PageConstant.DEFAULT_CONTROL_TEMPLATE_PATH
						+ PageConstant.URL_SPLIT + fullPath
						+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
				// find the control in the right store but default language
				if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
					return tmpPage;
				}
			}

			if (!PageConstant.DEFAULT_TEMPLATE_STORE.equals(storeName)) {
				tmpPage = PageConstant.DEFAULT_TEMPLATE_STORE
						+ PageConstant.URL_SPLIT + language
						+ PageConstant.DEFAULT_CONTROL_TEMPLATE_PATH
						+ PageConstant.URL_SPLIT + fullPath
						+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
				// find the control in the default store with right language
				if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
					return tmpPage;
				}
				if (!PageConstant.DEFAULT_TEMPLATE_LANGUAGE.equals(language)) {
					tmpPage = PageConstant.DEFAULT_TEMPLATE_STORE
							+ PageConstant.URL_SPLIT
							+ PageConstant.DEFAULT_TEMPLATE_LANGUAGE
							+ PageConstant.DEFAULT_CONTROL_TEMPLATE_PATH
							+ PageConstant.URL_SPLIT + fullPath
							+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
					// find the control in the default store but default
					// language
					if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
						return tmpPage;
					}
				}
			}
		}
		return null;
	}

	public static String getScreenTemplatePath(String servletPath) {
		// erase the suffix
		if (servletPath.startsWith(PageConstant.URL_SPLIT)) {
			servletPath = servletPath.substring(1, servletPath
					.lastIndexOf(PageConstant.DOT));
		} else {
			servletPath = servletPath.substring(0, servletPath
					.lastIndexOf(PageConstant.DOT));
		}

		String[] names = StringUtil.split(servletPath, PageConstant.URL_SPLIT);

		if (names != null && names.length > 2) {
			String storeName = names[0];
			String language = names[1];
			StringBuffer sb = new StringBuffer();
			for (int i = 2; i < names.length; i++) {
				sb.append(names[i]).append(PageConstant.URL_SPLIT);
			}
			String fullPath = sb.toString();
			fullPath = fullPath.substring(0, fullPath.length() - 1);

			String tmpPage = storeName + PageConstant.URL_SPLIT + language
					+ PageConstant.DEFAULT_SCREEN_TEMPLATE_PATH
					+ PageConstant.URL_SPLIT + fullPath
					+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
			// find the screen in the right store and language
			if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
				return tmpPage;
			}

			if (!PageConstant.DEFAULT_TEMPLATE_LANGUAGE.equals(language)) {
				tmpPage = storeName + PageConstant.URL_SPLIT
						+ PageConstant.DEFAULT_TEMPLATE_LANGUAGE
						+ PageConstant.DEFAULT_SCREEN_TEMPLATE_PATH
						+ PageConstant.URL_SPLIT + fullPath
						+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
				// find the screen in the right store but default language
				if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
					return tmpPage;
				}
			}

			if (!PageConstant.DEFAULT_TEMPLATE_STORE.equals(storeName)) {
				tmpPage = PageConstant.DEFAULT_TEMPLATE_STORE
						+ PageConstant.URL_SPLIT + language
						+ PageConstant.DEFAULT_SCREEN_TEMPLATE_PATH
						+ PageConstant.URL_SPLIT + fullPath
						+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
				// find the screen in the default store with right language
				if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
					return tmpPage;
				}
				if (!PageConstant.DEFAULT_TEMPLATE_LANGUAGE.equals(language)) {
					tmpPage = PageConstant.DEFAULT_TEMPLATE_STORE
							+ PageConstant.URL_SPLIT
							+ PageConstant.DEFAULT_TEMPLATE_LANGUAGE
							+ PageConstant.DEFAULT_SCREEN_TEMPLATE_PATH
							+ PageConstant.URL_SPLIT + fullPath
							+ ControllerConstant.DEFAULT_TEMPLATE_SUFFIX;
					// find the screen in the default store but default language
					if (RuntimeSingleton.getLoaderNameForResource(tmpPage) != null) {
						return tmpPage;
					}
				}
			}
		}
		return null;
	}

}
