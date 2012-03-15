package com.sillycat.corvus.service.impl;

import com.sillycat.aries.exceptions.InitializationException;
import com.sillycat.corvus.service.Initable;
import com.sillycat.corvus.service.InitableBroker;

public class BaseInitable implements Initable {
	/** InitableBroker that instantiatd this class. */
	protected InitableBroker initableBroker;

	/** Initialization status of this class. */
	protected boolean isInitialized = false;

	/**
	 * Default constructor of BaseInitable.
	 * 
	 * This constructor does nothing. Your own constructurs should be modest in
	 * allocating memory and other resources, leaving this to the
	 * <code>init()</code> method.
	 */
	public BaseInitable() {
	}

	/**
	 * Saves InitableBroker reference for later use.
	 * 
	 * @param broker
	 *            The InitableBroker that instantiated this object.
	 */
	public void setInitableBroker(InitableBroker broker) {
		this.initableBroker = broker;
	}

	/**
	 * Returns an InitableBroker reference.
	 * 
	 * @return The InitableBroker that instantiated this object.
	 */
	public InitableBroker getInitableBroker() {
		return initableBroker;
	}

	/**
	 * Performs early initialization. Used in a manner similar to a ctor.
	 * 
	 * BaseInitable doesn't need early initialization, therefore it ignores all
	 * objects passed to it and performs no initialization activities.
	 * 
	 * @param data
	 *            An Object to use for initialization activities.
	 * @exception InitializationException
	 *                Initialization of this class was not successful.
	 */
	public void init(Object data) throws InitializationException {
	}

	/**
	 * Performs late initializtion. Called when the Service is requested for the
	 * first time (if not already completely initialized by the early
	 * initializer).
	 * 
	 * Late intialization of a BaseInitable is alwas successful.
	 * 
	 * @exception InitializationException
	 *                Initialization of this class was not successful.
	 */
	public void init() throws InitializationException {
	}

	/**
	 * Returns an Initable to uninitialized state.
	 * 
	 * Calls setInit(false) to mark that we are no longer in initialized state.
	 */
	public void shutdown() {
		setInit(false);
	}

	/**
	 * Returns initialization status.
	 * 
	 * @return True if the initable is initialized.
	 */
	public boolean getInit() {
		return isInitialized;
	}

	/**
	 * Sets initailization status.
	 * 
	 * @param value
	 *            The new initialization status.
	 */
	protected void setInit(boolean value) {
		this.isInitialized = value;
	}
}
