package com.sillycat.corvus.web.modules;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.aries.constants.PageConstant;
import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.service.impl.CorvusServiceImpl;

public abstract class JavaBaseFactory implements AssemblerFactory {

	private static String[] packages = CorvusServiceImpl.getInstance()
			.getConfiguration().getStringArray("models.package");

	protected Log log = LogFactory.getLog(this.getClass());

	private Map<String, Object> classCache = Collections
			.synchronizedMap(new HashMap<String, Object>());

	private Assembler getAssember(String name) {
		Assembler assembler = null;
		for (int i = 0; i < packages.length; i++) {
			StringBuffer className = new StringBuffer();
			className.append(packages[i]);
			className.append('.');
			className.append(name);
			log.debug("Trying " + className);
			try {
				Class<?> servClass = (Class<?>) classCache.get(className
						.toString());
				if (servClass == null) {
					servClass = Class.forName(className.toString());
					classCache.put(className.toString(), servClass);
				}
				assembler = (Assembler) servClass.newInstance();
				break;
			} catch (ClassNotFoundException cnfe) {
				log.debug(className + ": Not found");
			} catch (NoClassDefFoundError ncdfe) {
				log.debug(className + ": No Class Definition found");
			} catch (ClassCastException cce) {
				log.error("Could not load " + className, cce);
				break;
			} catch (InstantiationException ine) {
				log.error("Could not load " + className, ine);
				break;
			} catch (IllegalAccessException ilae) {
				log.error("Could not load " + className, ilae);
				break;
			}
		}
		return assembler;
	}

	public Assembler getAssembler(String packageName, String name) {
		Assembler assembler = null;
		log.debug("Class Fragment is " + name);
		if (StringUtil.isNotBlank(name)) {
			assembler = getAssember(name);
			if (assembler == null) {
				// find screen in last package
				String tmp = name;
				assembler = getAssember(tmp);
				if (assembler != null) {
					return assembler;
				}
				String[] names = name.split(ControllerConstant.REGEX_DOT);
				if (names != null && names.length > 2) {
					String storeName = names[0];
					String language = names[1];
					StringBuffer sb = new StringBuffer();
					for (int i = 2; i < names.length; i++) {
						sb.append(names[i]).append(PageConstant.DOT);
					}
					String rest = sb.toString();
					rest = rest.substring(0, rest.length() - 1);
					if (!PageConstant.DEFAULT_TEMPLATE_LANGUAGE
							.equals(language)) {
						tmp = storeName + PageConstant.DOT
								+ PageConstant.DEFAULT_TEMPLATE_LANGUAGE
								+ PageConstant.DOT + rest;
						// store + default language
						assembler = getAssember(tmp);
						if (assembler != null) {
							return assembler;
						}
					}
					if (!PageConstant.DEFAULT_TEMPLATE_STORE.equals(storeName)) {
						tmp = PageConstant.DEFAULT_TEMPLATE_STORE
								+ PageConstant.DOT + language
								+ PageConstant.DOT + rest;
						assembler = getAssember(tmp);
						if (assembler != null) {
							return assembler;
						}
						if (!PageConstant.DEFAULT_TEMPLATE_LANGUAGE
								.equals(language)) {
							tmp = PageConstant.DEFAULT_TEMPLATE_STORE
									+ PageConstant.DOT
									+ PageConstant.DEFAULT_TEMPLATE_LANGUAGE
									+ PageConstant.DOT + rest;
							if (assembler != null) {
								return assembler;
							}
						}
					}
				}
				// find the framework default screen java class
				if (assembler == null
						&& packageName
								.equalsIgnoreCase(ControllerConstant.SCREEN)) {
					try {
						String className = "com.sillycat.corvus.web.modules.screen.DefaultScreen";
						Class<?> servClass = (Class<?>) classCache
								.get(className);
						if (servClass == null) {
							servClass = Class.forName(className);
							classCache.put(className, servClass);
						}
						assembler = (Assembler) servClass.newInstance();
					} catch (Exception e) {
						log.error("Could not load Default screen" + e);
					}
				}
				if (assembler == null
						&& packageName
								.equalsIgnoreCase(ControllerConstant.CONTORL)) {
					try {
						String className = "com.sillycat.corvus.web.modules.control.DefaultControl";
						Class<?> servClass = (Class<?>) classCache
								.get(className);
						if (servClass == null) {
							servClass = Class.forName(className);
							classCache.put(className, servClass);
						}
						assembler = (Assembler) servClass.newInstance();
					} catch (Exception e) {
						log.error("Could not load Default screen" + e);
					}
				}
			}
		}
		log.debug("Returning: " + assembler);
		return assembler;
	}
}
