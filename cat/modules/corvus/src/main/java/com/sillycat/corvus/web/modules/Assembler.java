package com.sillycat.corvus.web.modules;

import com.sillycat.corvus.web.DispatcherController;

/**
 * 
 * This is an interface that defines what an Assembler is. All the current
 * modules extend off of this class. It is currently empty and future use is yet
 * to be determined.
 * 
 * <p>
 * <a href="Assembler.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:magic_dreamer@126.com">Sillycat</a>
 * @version 2010-8-5:下午05:11:23
 */
public abstract class Assembler {

	protected DispatcherController servlet = null;

	/**
	 * @return Returns the servlet.
	 */
	public DispatcherController getServlet() {
		return servlet;
	}

	/**
	 * @param servlet
	 *            The servlet to set.
	 */
	public void setServlet(DispatcherController servlet) {
		this.servlet = servlet;
	}
}
