package com.sillycat.corvus.service;

import java.util.Properties;

import org.apache.commons.configuration.Configuration;

public interface Service extends Initable {
	
	/** The name of this service. */
	
	String SERVICE_NAME = "Service";

	/**
	 * Provides a Service with a reference to the ServiceBroker that
	 * instantiated this object, so that it can ask for its properties and
	 * access other Services.
	 * 
	 * @param broker
	 *            The ServiceBroker that instantiated this object.
	 */
	void setServiceBroker(ServiceBroker broker);

	/**
	 * ServiceBroker uses this method to pass a Service its name. Service uses
	 * its name to ask the broker for an apropriate set of Properties.
	 * 
	 * @param name
	 *            The name of this Service.
	 */
	void setName(String name);

	/**
	 * Returns the name of this Service.
	 * 
	 * @return The name of this Service.
	 */
	String getName();

	/**
	 * Returns the Properties of this Service. Every Service has at least one
	 * property, which is "classname", containing the name of the class
	 * implementing this service. Note that the service may chose to alter its
	 * properties, therefore they may be different from those returned by
	 * ServiceBroker.
	 * 
	 * @return The properties of this Service.
	 */
	Properties getProperties();

	/**
	 * Returns the Configuration of this Service. Every Service has at least one
	 * property, which is "classname", containing the name of the class
	 * implementing this service. Note that the service may chose to alter its
	 * configuration, therefore they may be different from those returned by
	 * ServiceBroker.
	 * 
	 * @return The Configuration of this Service.
	 */
	Configuration getConfiguration();
}
