package com.sillycat.corvus.web.rundata;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;

import com.sillycat.aries.exceptions.ServiceException;


public interface ParameterParser extends ValueParser
{
	/**
     * Gets the parsed servlet request.
     *
     * @return the parsed servlet request or null.
     */
    HttpServletRequest getRequest();

    /**
     * Sets the servlet request to be parser.  This requires a
     * valid HttpServletRequest object.  It will attempt to parse out
     * the GET/POST/PATH_INFO data and store the data into a Map.
     * There are convenience methods for retrieving the data as a
     * number of different datatypes.  The PATH_INFO data must be a
     * URLEncoded() string.
     *
     * <p>To add name/value pairs to this set of parameters, use the
     * <code>add()</code> methods.
     *
     * @param req An HttpServletRequest.
     */
    void setRequest(HttpServletRequest req) throws ServiceException;

    /**
     * Sets the uploadData byte[]
     *
     * @param uploadData A byte[] with data.
     */
    void setUploadData(byte[] uploadData);

    /**
     * Gets the uploadData byte[]
     *
     * @return uploadData A byte[] with data.
     */
    byte[] getUploadData();

    /**
     * Add a FileItem object as a parameters.  If there are any
     * FileItems already associated with the name, append to the
     * array.  The reason for this is that RFC 1867 allows multiple
     * files to be associated with single HTML input element.
     *
     * @param name A String with the name.
     * @param value A FileItem with the value.
     */
    void append(String name, FileItem value);

    /**
     * Return a FileItem object for the given name.  If the name does
     * not exist or the object stored is not a FileItem, return null.
     *
     * @param name A String with the name.
     * @return A FileItem.
     */
    FileItem getFileItem(String name);

    /**
     * Return an array of FileItem objects for the given name.  If the
     * name does not exist or the object stored is not a FileItem
     * array, return null.
     *
     * @param name A String with the name.
     * @return A FileItem[].
     */
    FileItem[] getFileItems(String name);

}
