package com.sillycat.corvus.service;

import org.apache.commons.configuration.Configuration;

import com.sillycat.aries.exceptions.InitializationException;

public interface ServiceManager extends ServiceBroker {
	/**
	 * Initialize this service manager.
	 */
	void init() throws InitializationException;

	/**
	 * Get the configuration for this service manager.
	 * 
	 * @return Manager configuration.
	 */
	Configuration getConfiguration();

	/**
	 * Set the configuration object for the services broker. This is the
	 * configuration that contains information about all services in the care of
	 * this service manager.
	 * 
	 * @param configuration
	 *            Manager configuration.
	 */
	void setConfiguration(Configuration configuration);

	/**
	 * Set the application root.
	 * 
	 * @param applicationRoot
	 *            application root
	 */
	void setApplicationRoot(String applicationRoot);
}
