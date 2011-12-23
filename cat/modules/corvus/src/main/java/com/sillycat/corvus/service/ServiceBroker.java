package com.sillycat.corvus.service;

import org.apache.commons.configuration.Configuration;

import com.sillycat.aries.exceptions.InitializationException;

public interface ServiceBroker {
	
	 /**
     * Determines whether a service is registered in the configured
     * <code>TurbineResources.properties</code>.
     * 
     * @param serviceName
     *            The name of the service whose existance to check.
     * @return Registration predicate for the desired services.
     */
    boolean isRegistered(String serviceName);

    /**
     * Performs early initialization of the specified service.
     * 
     * @param name
     *            The name of the service.
     * @exception InitializationException
     *                if the service is unknown or can't be initialized.
     */
    void initService(String name) throws InitializationException;

    /**
     * Shutdowns a Service.
     * 
     * This method is used to release resources allocated by a Service, and
     * return it to initial (uninitailized) state.
     * 
     * @param name
     *            The name of the Service to be uninitialized.
     */
    void shutdownService(String name);

    /**
     * Shutdowns all Services.
     * 
     * This method is used to release resources allocated by Services, and
     * return them to initial (uninitialized) state.
     */
    void shutdownServices();

    /**
     * Returns an instance of requested Service.
     * 
     * @param name
     *            The name of the Service requested.
     * @return An instance of requested Service.
     * @exception InstantiationException
     *                if the service is unknown or can't be initialized.
     */
    Service getService(String name) throws InstantiationException;

    /**
     * Returns the configuration of a specific service. Services use this method
     * to retrieve their configuration.
     * 
     * @param name
     *            The name of the service.
     * @return Configuration of the requested service.
     */
    Configuration getConfiguration(String name);

}
