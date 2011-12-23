package com.sillycat.corvus.web.rundata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.aries.constants.ControllerConstant;
import com.sillycat.aries.exceptions.ServiceException;
import com.sillycat.corvus.service.UploadService;
import com.sillycat.corvus.service.impl.BaseUpload;

public class DefaultParameterParser extends BaseValueParser implements
		ParameterParser {
	/** Logging */
	private static Log log = LogFactory.getLog(DefaultParameterParser.class);

	/** The servlet request to parse. */
	private HttpServletRequest request = null;

	/** The raw data of a file upload. */
	private byte[] uploadData = null;

	/** Map of request parameters to FileItem[]'s */
	private Map<String,Object> fileParameters = new HashMap<String,Object>();

	/** Turbine Upload Service reference */
	private static UploadService uploadService = null;

	/** Do we have an upload Service? */
	private static boolean uploadServiceIsAvailable = false;

	/** Java 1.4 encode method to use instead of deprecated 1.3 version. */
	private static Method decode = null;

	/*
	 * Initialize the encode variable with the 1.4 method if available. this
	 * code was adapted from org.apache.struts.utils.RequestUtils
	 */
	static {
		try {
			/* get version of encode method with two String args */
			Class<?>[] args = new Class[] { String.class, String.class };
			decode = URLDecoder.class.getMethod("decode", args);
		} catch (NoSuchMethodException e) {
			log
					.debug("Can't find JDK 1.4 encode method. Using JDK 1.3 version.");
		}
	}

	/**
	 * Create a new empty instance of ParameterParser. Uses the default
	 * character encoding (US-ASCII).
	 * 
	 * <p>
	 * To add name/value pairs to this set of parameters, use the
	 * <code>add()</code> methods.
	 */
	public DefaultParameterParser() {
		super();
		configureUploadService();
	}

	/**
	 * Create a new empty instance of ParameterParser. Takes a character
	 * encoding name to use when converting strings to bytes.
	 * 
	 * <p>
	 * To add name/value pairs to this set of parameters, use the
	 * <code>add()</code> methods.
	 * 
	 * @param characterEncoding
	 *            The character encoding of strings.
	 */
	public DefaultParameterParser(String characterEncoding) {
		super(characterEncoding);
		configureUploadService();
	}

	/**
	 * Checks for availability of the Upload Service. We do this check only once
	 * at Startup, because the getService() call is really expensive and we
	 * don't have to run it every time we process a request.
	 */
	private void configureUploadService() {
		uploadServiceIsAvailable = BaseUpload.isAvailable();
		if (uploadServiceIsAvailable) {
			uploadService = BaseUpload.getService();
		}
	}

	/**
	 * Disposes the parser.
	 */
	public void dispose() {
		this.request = null;
		this.uploadData = null;
		this.fileParameters.clear();
		super.dispose();
	}

	/**
	 * Gets the parsed servlet request.
	 * 
	 * @return the parsed servlet request or null.
	 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * Sets the servlet request to be parser. This requires a valid
	 * HttpServletRequest object. It will attempt to parse out the
	 * GET/POST/PATH_INFO data and store the data into a Map. There are
	 * convenience methods for retrieving the data as a number of different
	 * datatypes. The PATH_INFO data must be a URLEncoded() string.
	 * <p>
	 * To add name/value pairs to this set of parameters, use the
	 * <code>add()</code> methods.
	 * 
	 * @param request
	 *            An HttpServletRequest.
	 */
	public void setRequest(HttpServletRequest request) throws ServiceException {
		clear();

		uploadData = null;

		String enc = request.getCharacterEncoding();
		setCharacterEncoding(enc != null ? enc : ControllerConstant.ENCODING);

		// String object re-use at its best.
		String tmp = null;

		tmp = request.getHeader("Content-type");

		if (uploadServiceIsAvailable && uploadService.getAutomatic()
				&& tmp != null && tmp.startsWith("multipart/form-data")) {
			log.debug("Running the Turbine Upload Service");
			try {
				BaseUpload.parseRequest(request, this);
			} catch (ServiceException e) {
				log.error("File upload failed", e);
			}
		}

		for (Enumeration<?> names = request.getParameterNames(); names
				.hasMoreElements();) {
			tmp = (String) names.nextElement();
			add(convert(tmp), request.getParameterValues(tmp));
		}

		// Also cache any pathinfo variables that are passed around as
		// if they are query string data.
		try {
			StringTokenizer st = new StringTokenizer(request.getPathInfo(), "/");
			boolean isNameTok = true;
			String pathPart = null;
			while (st.hasMoreTokens()) {
				if (isNameTok) {
					tmp = decode(st.nextToken(), request);
					isNameTok = false;
				} else {
					pathPart = decode(st.nextToken(), request);

					if (tmp.length() > 0) {
						add(convert(tmp), pathPart);
					}
					isNameTok = true;
				}
			}
		} catch (Exception e) {
			// If anything goes wrong above, don't worry about it.
			// Chances are that the path info was wrong anyways and
			// things that depend on it being right will fail later
			// and should be caught later.
		}

		this.request = request;

		if (log.isDebugEnabled()) {
			log.debug("Parameters found in the Request:");
			for (Iterator<?> it = keySet().iterator(); it.hasNext();) {
				String key = (String) it.next();
				log.debug("Key: " + key + " -> " + getString(key));
			}
		}
	}

	private String decode(String decoder, HttpServletRequest request) {
		if (decode != null) {
			try {
				Object[] args = new Object[] { decoder,
						request.getCharacterEncoding() };
				return (String) decode.invoke(null, args);
			} catch (IllegalAccessException e) {
				// don't keep trying if we get one of these
				decode = null;

				log.debug("Can't access JDK 1.4 encode method (" + e
						+ "). Using deprecated version from now on.");
			} catch (InvocationTargetException e) {
				log.debug("Error using JDK 1.4 encode method (" + e
						+ "). Using deprecated version.");
			}
		}
		return decoder;
	}

	/**
	 * Sets the uploadData byte[]
	 * 
	 * @param uploadData
	 *            A byte[] with data.
	 */
	public void setUploadData(byte[] uploadData) {
		this.uploadData = uploadData;
	}

	/**
	 * Gets the uploadData byte[]
	 * 
	 * @return uploadData A byte[] with data.
	 */
	public byte[] getUploadData() {
		return this.uploadData;
	}

	/**
	 * Add a FileItem object as a parameters. If there are any FileItems already
	 * associated with the name, append to the array. The reason for this is
	 * that RFC 1867 allows multiple files to be associated with single HTML
	 * input element.
	 * 
	 * @param name
	 *            A String with the name.
	 * @param value
	 *            A FileItem with the value.
	 */
	public void append(String name, FileItem value) {
		FileItem[] items = this.getFileItems(name);
		if (items == null) {
			items = new FileItem[1];
			items[0] = value;
			fileParameters.put(convert(name), items);
		} else {
			FileItem[] newItems = new FileItem[items.length + 1];
			System.arraycopy(items, 0, newItems, 0, items.length);
			newItems[items.length] = value;
			fileParameters.put(convert(name), newItems);
		}
	}

	/**
	 * Return a FileItem object for the given name. If the name does not exist
	 * or the object stored is not a FileItem, return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A FileItem.
	 */
	public FileItem getFileItem(String name) {
		try {
			FileItem value = null;
			Object object = fileParameters.get(convert(name));
			if (object != null) {
				value = ((FileItem[]) object)[0];
			}
			return value;
		} catch (ClassCastException e) {
			log.error(
					"Parameter (" + name + ") is not an instance of FileItem",
					e);
			return null;
		}
	}

	/**
	 * Return an array of FileItem objects for the given name. If the name does
	 * not exist or the object stored is not a FileItem array, return null.
	 * 
	 * @param name
	 *            A String with the name.
	 * @return A FileItem[].
	 */
	public FileItem[] getFileItems(String name) {
		try {
			return (FileItem[]) fileParameters.get(convert(name));
		} catch (ClassCastException e) {
			log.error("Parameter (" + name
					+ ") is not an instance of FileItem[]", e);
			return null;
		}
	}
}