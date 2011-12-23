package com.sillycat.corvus.service;

import com.sillycat.aries.exceptions.InitializationException;

public interface InitableBroker {
	/**
	 * Performs early initialization of an Initable class.
	 * 
	 * If your class depends on another Initable being initialized to perform
	 * early initialization, you should always ask your broker to initialize the
	 * other class with the objects that are passed to you, before you try to
	 * retrieve that Initable's instance with getInitable().
	 * 
	 * @param className
	 *            The name of the class to be initailized.
	 * @param data
	 *            An object to be used for initialization activities.
	 * @exception InitializationException
	 *                if initialization of this class was not successful.
	 */
	void initClass(String className, Object data)
			throws InitializationException;

	/**
	 * Shutdowns an Initable class.
	 * 
	 * This method is used to release resources allocated by an Initable class,
	 * and return it to initial (uninitailized) state.
	 * 
	 * @param className
	 *            The name of the class to be uninitialized.
	 */
	void shutdownClass(String className);

	/**
	 * Provides an instance of Initable class ready to work.
	 * 
	 * If the requested class couldn't be instatiated or initialized,
	 * InstantiationException will be thrown. You needn't handle this exception
	 * in your code, since it indicates fatal misconfigurtion of the system.
	 * 
	 * @param className
	 *            The name of the Initable requested.
	 * @return An instance of requested Initable.
	 * @exception InstantiationException
	 *                if there was a problem during instantiation or
	 *                initialization of the Initable.
	 */
	Initable getInitable(String className) throws InstantiationException;
}
