package com.sillycat.corvus.service.impl;

import javax.servlet.http.HttpServletRequest;

import com.sillycat.aries.exceptions.ServiceException;
import com.sillycat.aries.exceptions.ServiceRunTimeException;
import com.sillycat.corvus.service.UploadService;
import com.sillycat.corvus.web.rundata.ParameterParser;

public abstract class BaseUpload {
	 /**
     * <p>
     * Retrieves an instance of system's configured implementation of
     * <code>UploadService</code>
     * 
     * @return An instance of UploadService
     */
    public static UploadService getService() {
        try {
            return (UploadService) CorvusServiceImpl.getInstance().getService(
                    UploadService.SERVICE_NAME);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e1){
            e1.printStackTrace();
        }
        return null;
    }

    /**
     * Checks whether an Upload Service is configured. This method is safe to
     * call even with no Upload service installed.
     * 
     * @return True if an upload Service is configured
     */
    public static boolean isAvailable() {
        try {
            getService();
        } catch (ServiceRunTimeException ie) {
            // If the service couldn't be instantiated, it obviously
            // isn't configured.
            return false;
        }
        return true;
    }

    /**
     * Retrieves the value of the 'automatic' property of {@linkUploadService}.
     * This reports whether the Upload Service is available and (if yes), the
     * Parameter parser should allow "automatic" uploads if it is submitted to
     * Turbine.
     * 
     * This method is safe to call even with no Upload Service configured.
     * 
     * @return The value of 'automatic' property of {@linkUploadService}.
     */
    public static boolean getAutomatic() {
        // Short circuit evaluation of the && operator!
        return isAvailable() && getService().getAutomatic();
    }

    /**
     * <p>
     * Retrieves the value of 'size.max' property of {@linkUploadService}.
     * 
     * @return The value of 'size.max' property of {@linkUploadService}.
     */
    public static long getSizeMax() {
        return getService().getSizeMax();
    }

    /**
     * <p>
     * Retrieves the value of <code>size.threshold</code> property of
     * {@link org.apache.turbine.services.upload.UploadService}.
     * 
     * @return The threshold beyond which files are written directly to disk.
     */
    public static int getSizeThreshold() {
        return getService().getSizeThreshold();
    }

    /**
     * <p>
     * Retrieves the value of the <code>repository</code> property of
     * {@link org.apache.turbine.services.upload.UploadService}.
     * 
     * @return The repository.
     */
    public static String getRepository() {
        return getService().getRepository();
    }

    /**
     * <p>
     * Performs parsing the request and storing files and form fields. Default
     * file repository is used. This method is called by the
     * {@link ParameterParser}if automatic upload is enabled.
     * 
     * @param req
     *            The servlet request to be parsed.
     * @param params
     *            The ParameterParser instance to insert form fields into.
     * @exception TurbineException
     *                If there are problems reading/parsing the request or
     *                storing files.
     */
    public static void parseRequest(HttpServletRequest req,
            ParameterParser params) throws ServiceException {
        UploadService upload = getService();
        upload.parseRequest(req, params, upload.getRepository());
    }

    /**
     * <p>
     * Performs parsing the request and storing files and form fields. Custom
     * file repository may be specified. You can call this method in your file
     * upload {@linkorg.apache.turbine.modules.Action} to if you need to
     * specify a custom directory for storing files.
     * 
     * @param req
     *            The servlet request to be parsed.
     * @param params
     *            The ParameterParser instance to insert form fields into.
     * @param path
     *            The location where the files should be stored.
     * @exception TurbineException
     *                If there are problems reading/parsing the request or
     *                storing files.
     */
    public static void parseRequest(HttpServletRequest req,
            ParameterParser params, String path) throws ServiceException {
        getService().parseRequest(req, params, path);
    }
}
