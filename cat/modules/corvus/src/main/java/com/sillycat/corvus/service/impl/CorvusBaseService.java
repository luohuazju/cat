package com.sillycat.corvus.service.impl;

import javax.servlet.ServletConfig;

import com.sillycat.aries.exceptions.InitializationException;
import com.sillycat.corvus.models.RunData;

public abstract class CorvusBaseService extends BaseService {
	/**
	 * Performs early initialization. Overrides init() method in BaseService to
	 * detect objects used in Turbine's Service initialization and pass them to
	 * apropriate init() methods.
	 * 
	 * @param data
	 *            An Object to use for initialization activities.
	 * @exception InitializationException
	 *                if initialization of this class was not successful.
	 */
	public void init(Object data) throws InitializationException {
		if (data instanceof ServletConfig) {
			init((ServletConfig) data);
		} else if (data instanceof RunData) {
			init((RunData) data);
		}
	}

	/**
	 * Performs early initialization.
	 * 
	 * @param data
	 *            An RunData to use for initialization activities.
	 * @exception InitializationException
	 *                if initialization of this class was not successful.
	 */
	public void init(RunData data) throws InitializationException {
	}

	/**
	 * Performs late initialization.
	 * 
	 * If your class relies on early initialization, and the object it expects
	 * was not received, you can use late initialization to throw an exception
	 * and complain.
	 * 
	 * @exception InitializationException
	 *                , if initialization of this class was not successful.
	 */
	public void init() throws InitializationException {
		setInit(true);
	}

	/**
	 * Returns to uninitialized state.
	 * 
	 * You can use this method to release resources thet your Service allocated
	 * when Turbine shuts down.
	 */
	public void shutdown() {
		setInit(false);
	}
}
