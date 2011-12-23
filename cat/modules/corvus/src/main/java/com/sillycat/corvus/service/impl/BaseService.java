package com.sillycat.corvus.service.impl;

import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationConverter;

import com.sillycat.corvus.service.Service;
import com.sillycat.corvus.service.ServiceBroker;

public class BaseService extends BaseInitable implements Service {

	/** A reference to the ServiceBroker that instantiated this object. */
	protected ServiceBroker serviceBroker;

	/** The configuration for this service */
	protected Configuration configuration;

	/** The name of this Service. */
	protected String name;

	/**
	 * Saves a reference to the ServiceBroker that instantiated this object, so
	 * that it can ask for its properties and access other Services.
	 * 
	 * @param broker
	 *            The ServiceBroker that instantiated this object.
	 */
	public void setServiceBroker(ServiceBroker broker) {
		this.serviceBroker = broker;
	}

	/**
	 * ServiceBroker uses this method to pass a Service its name.
	 * 
	 * @param name
	 *            The name of this Service.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of this service.
	 * 
	 * @return The name of this Service.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns a ServiceBroker reference.
	 * 
	 * @return The ServiceBroker that instantiated this object.
	 */
	public ServiceBroker getServiceBroker() {
		return serviceBroker;
	}

	/**
	 * Returns the properties of this Service.
	 * 
	 * @return The Properties of this Service.
	 */
	public Properties getProperties() {
		return ConfigurationConverter.getProperties(getConfiguration());
	}

	/**
	 * Returns the configuration of this Service.
	 * 
	 * @return The Configuration of this Service.
	 */
	public Configuration getConfiguration() {
		if (name == null) {
			return null;
		}
		if (configuration == null) {
			configuration = getServiceBroker().getConfiguration(name);
		}
		return configuration;
	}
}
