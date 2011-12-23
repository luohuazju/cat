package com.sillycat.corvus.service.impl;

import com.sillycat.corvus.service.ServiceManager;

public class CorvusServiceImpl extends BaseServiceBroker implements
		ServiceManager {

	/** The single instance of this class. */
	private static ServiceManager instance = new CorvusServiceImpl();

	/**
	 * This constructor is protected to force clients to use getInstance() to
	 * access this class.
	 */
	protected CorvusServiceImpl() {
		super();
	}

	/**
	 * The method through which this class is accessed as a broker.
	 * 
	 * @return The single instance of this class.
	 */
	public static ServiceManager getInstance() {
		return instance;
	}

	/**
	 * The method through which to change the default manager. Note that
	 * services of the previous manager will be shutdown.
	 * 
	 * @param manager
	 *            a new service manager.
	 */
	public static synchronized void setManager(ServiceManager manager) {
		ServiceManager previous = instance;
		instance = manager;
		if (previous != null) {
			previous.shutdownServices();
		}
	}

}
