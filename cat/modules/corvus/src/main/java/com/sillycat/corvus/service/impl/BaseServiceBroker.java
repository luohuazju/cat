package com.sillycat.corvus.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.aries.exceptions.InitializationException;
import com.sillycat.aries.exceptions.ServiceRunTimeException;
import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.service.Service;
import com.sillycat.corvus.service.ServiceBroker;

public abstract class BaseServiceBroker implements ServiceBroker {
	/**
	 * Mapping of Service names to class names.
	 */
	protected Configuration mapping = new BaseConfiguration();

	/**
	 * A repository of Service instances.
	 */
	protected Hashtable<String,Object> services = new Hashtable<String,Object>();

	/**
	 * Configuration for the services broker. The configuration should be set by
	 * the application in which the services framework is running.
	 */
	protected Configuration configuration;

	/**
	 * A prefix for <code>Service</code> properties in
	 * TurbineResource.properties.
	 */
	public static final String SERVICE_PREFIX = "services.";

	/**
	 * A <code>Service</code> property determining its implementing class name .
	 */
	public static final String CLASSNAME_SUFFIX = ".classname";

	/**
	 * These are objects that the parent application can provide so that
	 * application specific services have a mechanism to retrieve specialized
	 * information. For example, in Turbine there are services that require the
	 * RunData object: these services can retrieve the RunData object that
	 * Turbine has placed in the service manager. This alleviates us of the
	 * requirement of having init(Object) all together.
	 */
	protected Hashtable<String,Object> serviceObjects = new Hashtable<String,Object>();

	/** Logging */
	private static Log log = LogFactory.getLog(BaseServiceBroker.class);

	/**
	 * Application root path as set by the parent application.
	 */
	protected String applicationRoot;

	/**
	 * Default constructor, protected as to only be useable by subclasses.
	 * 
	 * This constructor does nothing.
	 */
	protected BaseServiceBroker() {
	}

	/**
	 * Set the configuration object for the services broker. This is the
	 * configuration that contains information about all services in the care of
	 * this service manager.
	 * 
	 * @param configuration
	 *            Broker configuration.
	 */
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Get the configuration for this service manager.
	 * 
	 * @return Broker configuration.
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * Initialize this service manager.
	 */
	public void init() throws InitializationException {
		// Check:
		//
		// 1. The configuration has been set.
		// 2. Make sure the application root has been set.

		// FIXME: Make some service framework exceptions to throw in
		// the event these requirements aren't satisfied.

		// Create the mapping between service names
		// and their classes.
		initMapping();

		try {
			// Start services that have their 'earlyInit'
			// property set to 'true'.
			initServices(false);
		} catch (InitializationException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set an application specific service object that can be used by
	 * application specific services.
	 * 
	 * @param name
	 *            name of service object
	 * @param value
	 *            value of service object
	 */
	public void setServiceObject(String name, Object value) {
		serviceObjects.put(name, value);
	}

	/**
	 * Get an application specific service object.
	 * 
	 * @return Object application specific service object
	 */
	public Object getServiceObject(String name) {
		return serviceObjects.get(name);
	}

	/**
	 * Creates a mapping between Service names and class names.
	 * 
	 * The mapping is built according to settings present in
	 * TurbineResources.properties. The entries should have the following form:
	 * 
	 * <pre>
	 * 
	 *  services.MyService.classname=com.mycompany.MyServiceImpl
	 *  services.MyOtherService.classname=com.mycompany.MyOtherServiceImpl
	 * 
	 * </pre>
	 * 
	 * <br>
	 * 
	 * Generic ServiceBroker provides no Services.
	 */
	protected void initMapping() {
		/*
		 * These keys returned in an order that corresponds to the order the
		 * services are listed in the TR.props.
		 * 
		 * When the mapping is created we use a Configuration object to ensure
		 * that the we retain the order in which the order the keys are
		 * returned.
		 * 
		 * There's no point in retrieving an ordered set of keys if they aren't
		 * kept in order :-)
		 */
		for (Iterator<?> keys = configuration.getKeys(); keys.hasNext();) {
			String key =  (String) keys.next();
			String[] keyParts = StringUtils.split(key, ".");

			if ((keyParts.length == 3)
					&& (keyParts[0] + ".").equals(SERVICE_PREFIX)
					&& ("." + keyParts[2]).equals(CLASSNAME_SUFFIX)) {
				String serviceKey = keyParts[1];
				log.info("Added Mapping for Service: " + serviceKey);

				if (!mapping.containsKey(serviceKey)) {
					mapping.setProperty(serviceKey, configuration
							.getString(key));
				}
			}
		}
	}

	/**
	 * Determines whether a service is registered in the configured
	 * <code>TurbineResources.properties</code>.
	 * 
	 * @param serviceName
	 *            The name of the service whose existance to check.
	 * @return Registration predicate for the desired services.
	 */
	public boolean isRegistered(String serviceName) {
		return (services.get(serviceName) != null);
	}

	/**
	 * Returns an Iterator over all known service names.
	 * 
	 * @return An Iterator of service names.
	 */
	public Iterator<?> getServiceNames() {
		return mapping.getKeys();
	}

	/**
	 * Returns an Iterator over all known service names beginning with the
	 * provided prefix.
	 * 
	 * @param prefix
	 *            The prefix against which to test.
	 * @return An Iterator of service names which match the prefix.
	 */
	public Iterator<?> getServiceNames(String prefix) {
		return mapping.getKeys(prefix);
	}

	/**
	 * Performs early initialization of specified service.
	 * 
	 * @param name
	 *            The name of the service (generally the
	 *            <code>SERVICE_NAME</code> constant of the service's interface
	 *            definition).
	 * @exception InitializationException
	 *                Initialization of this service was not successful.
	 * @throws
	 */
	public synchronized void initService(String name)
			throws InitializationException {
		// Calling getServiceInstance(name) assures that the Service
		// implementation has its name and broker reference set before
		// initialization.
		try {
			Service instance;
			instance = getServiceInstance(name);
			if (!instance.getInit()) {
				// this call might result in an indirect recursion
				instance.init();
			}
		} catch (InstantiationException e) {
			throw new InitializationException("initService " + name + " error",
					e);
		}
	}

	/**
	 * Performs early initialization of all services. Failed early
	 * initialization of a Service may be non-fatal to the system, thus any
	 * exceptions are logged and the initialization process continues.
	 */
	public void initServices() {
		try {
			initServices(false);
		} catch (InstantiationException notThrown) {
			log.debug("Caught non fatal exception", notThrown);
		} catch (InitializationException notThrown) {
			log.debug("Caught non fatal exception", notThrown);
		}
	}

	/**
	 * Performs early initialization of all services. You can decide to handle
	 * failed initializations if you wish, but then after one service fails, the
	 * other will not have the chance to initialize.
	 * 
	 * @param report
	 *            <code>true</code> if you want exceptions thrown.
	 */
	public void initServices(boolean report) throws InstantiationException,
			InitializationException {
		if (report) {
			// Throw exceptions
			for (Iterator<?> names = getServiceNames(); names.hasNext();) {
				doInitService(StringUtil.getTrimString(names.next()));
			}
		} else {
			// Eat exceptions
			for (Iterator<?> names = getServiceNames(); names.hasNext();) {
				try {
					doInitService(StringUtil.getTrimString(names.next()));
				}
				// In case of an exception, file an error message; the
				// system may be still functional, though.
				catch (InstantiationException e) {
					log.error(e);
				} catch (InitializationException e) {
					log.error(e);
				}
			}
		}
		log.info("Finished initializing all services!");
	}

	/**
	 * Internal utility method for use in {@link #initServices(boolean)}to
	 * prevent duplication of code.
	 */
	private void doInitService(String name) throws InstantiationException,
			InitializationException {
		// Only start up services that have their earlyInit flag set.
		if (getConfiguration(name).getBoolean("earlyInit", false)) {
			log.info("Start Initializing service (early): " + name);
			initService(name);
			log.info("Finish Initializing service (early): " + name);
		}
	}

	/**
	 * Shuts down a <code>Service</code>, releasing resources allocated by an
	 * <code>Service</code>, and returns it to its initial (uninitialized)
	 * state.
	 * 
	 * @param name
	 *            The name of the <code>Service</code> to be uninitialized.
	 */
	public synchronized void shutdownService(String name) {
		try {
			Service service = getServiceInstance(name);
			if (service != null && service.getInit()) {
				service.shutdown();
				if (service.getInit() && service instanceof BaseService) {
					// BaseService::shutdown() does this by default,
					// but could've been overriden poorly.
					((BaseService) service).setInit(false);
				}
			}
		} catch (InstantiationException e) {
			// Assuming harmless -- log the error and continue.
			log.error("Shutdown of a nonexistent Service '" + name
					+ "' was requested", e);
		}
	}

	/**
	 * Shuts down all Turbine services, releasing allocated resources and
	 * returning them to their initial (uninitialized) state.
	 */
	public void shutdownServices() {
		log.info("Shutting down all services!");

		String serviceName = null;

		/*
		 * Now we want to reverse the order of this list. This functionality
		 * should be added to the ExtendedProperties in the commons but this
		 * will fix the problem for now.
		 */

		ArrayList<String> reverseServicesList = new ArrayList<String>();

		for (Iterator<?> serviceNames = getServiceNames(); serviceNames.hasNext();) {
			serviceName = (String) serviceNames.next();
			reverseServicesList.add(0, serviceName);
		}

		for (Iterator<?> serviceNames = reverseServicesList.iterator(); serviceNames
				.hasNext();) {
			serviceName = (String) serviceNames.next();
			log.info("Shutting down service: " + serviceName);
			shutdownService(serviceName);
		}
	}

	/**
	 * Returns an instance of requested Service.
	 * 
	 * @param name
	 *            The name of the Service requested.
	 * @return An instance of requested Service.
	 * @exception InstantiationException
	 *                if the service is unknown or can't be initialized.
	 */
	public Service getService(String name) throws InstantiationException {
		Service service;
		try {
			service = getServiceInstance(name);
			if (!service.getInit()) {
				synchronized (service.getClass()) {
					if (!service.getInit()) {
						log.info("Start Initializing service (late): " + name);
						service.init();
						log.info("Finish Initializing service (late): " + name);
					}
				}
			}
			if (!service.getInit()) {
				// this exception will be caught & rethrown by this very method.
				// getInit() returning false indicates some initialization
				// issue,
				// which in turn prevents the InitableBroker from passing a
				// reference to a working instance of the initable to the
				// client.
				throw new InitializationException(
						"init() failed to initialize service " + name);
			}
			return service;
		} catch (InitializationException e) {
			throw new ServiceRunTimeException("Service " + name
					+ " failed to initialize", e);
		}
	}

	/**
	 * Retrieves an instance of a Service without triggering late
	 * initialization.
	 * 
	 * Early initialization of a Service can require access to Service
	 * properties. The Service must have its name and serviceBroker set by then.
	 * Therefore, before calling Initable.initClass(Object), the class must be
	 * instantiated with InitableBroker.getInitableInstance(), and
	 * Service.setServiceBroker() and Service.setName() must be called. This
	 * calls for two - level accessing the Services instances.
	 * 
	 * @param name
	 *            The name of the service requested.
	 * @exception InstantiationException
	 *                The service is unknown or can't be initialized.
	 */
	protected Service getServiceInstance(String name)
			throws InstantiationException {
		Service service = (Service) services.get(name);

		if (service == null) {
			String className = mapping.getString(name);
			if (StringUtils.isEmpty(className)) {
				throw new InstantiationException(
						"ServiceBroker: unknown service " + name + " requested");
			}
			try {
				service = (Service) services.get(className);

				if (service == null) {
					try {
						service = (Service) Class.forName(className)
								.newInstance();
					}
					// those two errors must be passed to the VM
					catch (ThreadDeath t) {
						throw t;
					} catch (OutOfMemoryError t) {
						throw t;
					} catch (Throwable t) {
						// Used to indicate error condition.
						String msg = null;

						if (t instanceof NoClassDefFoundError) {
							msg = "A class referenced by "
									+ className
									+ " is unavailable. Check your jars and classes.";
						} else if (t instanceof ClassNotFoundException) {
							msg = "Class "
									+ className
									+ " is unavailable. Check your jars and classes.";
						} else if (t instanceof ClassCastException) {
							msg = "Class "
									+ className
									+ " doesn't implement the Service interface";
						} else {
							msg = "Failed to instantiate " + className;
						}

						throw new ServiceRunTimeException(msg, t);
					}
				}
			} catch (ClassCastException e) {
				throw new ServiceRunTimeException("ServiceBroker: Class "
						+ className + " does not implement Service interface.",
						e);
			} catch (ServiceRunTimeException e) {
				throw new ServiceRunTimeException(
						"Failed to instantiate service " + name, e);
			}
			service.setServiceBroker(this);
			service.setName(name);
			services.put(name, service);
		}

		return service;
	}

	/**
	 * Returns the configuration for the specified service.
	 * 
	 * @param name
	 *            The name of the service.
	 * @return Configuration of requested Service.
	 */
	public Configuration getConfiguration(String name) {
		return configuration.subset(SERVICE_PREFIX + name);
	}

	/**
	 * Set the application root.
	 * 
	 * @param applicationRoot
	 *            application root
	 */
	public void setApplicationRoot(String applicationRoot) {
		this.applicationRoot = applicationRoot;
	}

	/**
	 * Get the application root as set by the parent application.
	 * 
	 * @return String application root
	 */
	public String getApplicationRoot() {
		return applicationRoot;
	}
}
