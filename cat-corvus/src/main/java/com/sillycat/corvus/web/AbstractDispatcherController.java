package com.sillycat.corvus.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeSingleton;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.corvus.models.ServerData;
import com.sillycat.corvus.service.ServiceManager;
import com.sillycat.corvus.service.impl.CorvusServiceImpl;

public abstract class AbstractDispatcherController extends HttpServlet
		implements ControllerConstant {

	private static final long serialVersionUID = -1899024268574630787L;

	private static String applicationRoot;

	private static ServletConfig servletConfig;

	private static ServerData serverData;

	private static Configuration configuration = null;

	protected static final Log log = LogFactory
			.getLog(AbstractDispatcherController.class);

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		deal(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		deal(request, response);
	}

	protected abstract void deal(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException;

	protected void setContentType(HttpServletRequest request,
			HttpServletResponse response) {
		String contentType = RuntimeSingleton.getString(CONTENT_TYPE,
				DEFAULT_CONTENT_TYPE);
		int index = contentType.lastIndexOf(';') + 1;
		if (index <= 0
				|| (index < contentType.length() && contentType.indexOf(
						"charset", index) == -1)) {
			// Append the character encoding which we'd like to use.
			String encoding = request.getCharacterEncoding();
			if (!DEFAULT_OUTPUT_ENCODING.equalsIgnoreCase(encoding)) {
				contentType += "; charset=" + encoding;
			} else {
				contentType += "; charset=" + DEFAULT_OUTPUT_ENCODING;
			}
		}
		response.setContentType(contentType);
	}

	public Template getTemplate(String name, String encoding)
			throws ResourceNotFoundException, ParseErrorException, Exception {
		return RuntimeSingleton.getTemplate(name, encoding);
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		servletConfig = config;
		initVelocity(config.getServletContext());
		applicationRoot = config.getServletContext().getRealPath("/");
		getServiceManager().setApplicationRoot(applicationRoot);
		try {
			configuration = (Configuration) new PropertiesConfiguration(
					DEFAULT_VORVUS_CONFIG_FILE);
			addDefaultVorvusConfig();
			getServiceManager().setConfiguration(configuration);
			getServiceManager().init();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void addDefaultVorvusConfig() throws IOException {
		InputStream is = AbstractDispatcherController.class.getClassLoader()
				.getResourceAsStream(
						"com/sillycat/corvus/web/corvus.properties");
		Properties defaultConf = new Properties();
		defaultConf.load(is);
		for (Iterator<Object> itr = defaultConf.keySet().iterator(); itr
				.hasNext();) {
			String key = (String) itr.next();
			String value = defaultConf.getProperty(key);
			if (!configuration.containsKey(key)) {
				configuration.setProperty(key, value);
			}
		}
	}

	private ServiceManager getServiceManager() {
		return CorvusServiceImpl.getInstance();
	}

	protected void initVelocity(ServletContext context) throws ServletException {
		try {
			Properties props = loadConfiguration(context);
			Velocity.init(props);
		} catch (Exception e) {
			throw new ServletException("Error Initializing Velocity: " + e, e);
		}
	}

	protected Properties loadConfiguration(ServletContext context)
			throws IOException, FileNotFoundException {
		if (context == null) {
			String gripe = "Error attempting to create a loadConfiguration from a null ServletContext!";
			log.error(gripe);
			throw new IllegalArgumentException(gripe);
		}
		Properties properties = new Properties();
		String configfile = DEFAULT_VELOCITY_CONFIG_FILE;
		InputStream in = null;
		try {
			in = AbstractDispatcherController.class.getClassLoader()
					.getResourceAsStream(configfile);
			if (in != null) {
				log.info("Initializing velocity using '" + configfile + "'");
				properties.load(in);
			}
		} catch (IOException e) {
			log.warn("Unable to load velocity configuration file '"
					+ configfile + "'", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		if (log.isDebugEnabled()) {
			log
					.debug("Initializing Velocity with the following properties ...");
			for (Iterator<Object> iter = properties.keySet().iterator(); iter
					.hasNext();) {
				String key = (String) iter.next();
				String value = properties.getProperty(key);

				if (log.isDebugEnabled()) {
					log.debug("    '" + key + "' = '" + value + "'");
				}
			}
		}
		return properties;
	}

	/**
	 * @return Returns the applicationRoot.
	 */
	public static String getApplicationRoot() {
		return applicationRoot;
	}

	public static String getRealPath(String path) {
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		return new File(getApplicationRoot(), path).getAbsolutePath();
	}

	/**
	 * @return Returns the servletConfig.
	 */
	public static ServletConfig getRubbishServletConfig() {
		return servletConfig;
	}

	public static ServerData getDefaultServerData() {
		if (serverData == null) {
			log
					.error("ServerData Information requested from Turbine before first request!");
			// Will be overwritten once the first request is run;
			serverData = new ServerData(null, ControllerConstant.HTTP_PORT,
					ControllerConstant.HTTP, null, null);
		}
		return serverData;
	}

	protected void error(HttpServletRequest request,
			HttpServletResponse response, Exception cause)
			throws ServletException, IOException {
		StringBuffer html = new StringBuffer();
		html.append("<html>");
		html.append("<title>Error</title>");
		html.append("<body bgcolor=\"#ffffff\">");
		html
				.append("<h2>DispatcherController: Error processing the template</h2>");
		html
				.append("<a href=\"mailto:magic_dreamer@126.com\">Please contact Admin</a>");
		html.append("<pre>");
		String why = cause.getMessage();
		if (why != null && why.trim().length() > 0) {
			html.append(why);
			html.append("<br>");
		}
		StringWriter sw = new StringWriter();
		cause.printStackTrace(new PrintWriter(sw));
		html.append(sw.toString());
		html.append("</pre>");
		html.append("</body>");
		html.append("</html>");
		response.getOutputStream().print(html.toString());
	}

}
