package com.sillycat.corvus.service.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.aries.exceptions.InitializationException;
import com.sillycat.aries.exceptions.ServiceException;
import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.service.UploadService;
import com.sillycat.corvus.web.AbstractDispatcherController;
import com.sillycat.corvus.web.rundata.ParameterParser;

public class UploadServiceImpl extends CorvusBaseService implements UploadService {
	private static Log log = LogFactory.getLog(UploadServiceImpl.class);

	DiskFileItemFactory factory = null;

	ServletFileUpload upload = null;

	/** Auto Upload yes? */
	private boolean automatic;

	/**
	 * Initializes the service.
	 * 
	 * This method processes the repository path, to make it relative to the web
	 * application root, if neccessary
	 */
	public void init() throws InitializationException {
		Configuration conf = getConfiguration();
		String repoPath = conf.getString(UploadService.REPOSITORY_KEY,
				UploadService.REPOSITORY_DEFAULT);

		if (!repoPath.startsWith("/")) {
			// If our temporary directory is in the application
			// space, try to create it. If this fails, throw
			// an exception.
			String testPath = AbstractDispatcherController.getRealPath(repoPath);
			File testDir = new File(testPath);
			if (!testDir.exists()) {
				if (!testDir.mkdirs()) {
					throw new InitializationException(
							"Could not create target directory!");
				}
			}
			repoPath = testPath;
			conf.setProperty(UploadService.REPOSITORY_KEY, repoPath);
		}

		log.debug("Upload Path is now " + repoPath);

		long sizeMax = conf.getLong(UploadService.SIZE_MAX_KEY,
				UploadService.SIZE_MAX_DEFAULT);

		log.debug("Max Size " + sizeMax);

		int sizeThreshold = conf.getInt(UploadService.SIZE_THRESHOLD_KEY,
				UploadService.SIZE_THRESHOLD_DEFAULT);

		log.debug("Threshold Size " + sizeThreshold);

		automatic = conf.getBoolean(UploadService.AUTOMATIC_KEY,
				UploadService.AUTOMATIC_DEFAULT);

		log.debug("Auto Upload " + automatic);
		factory = new DiskFileItemFactory();
		factory.setSizeThreshold(sizeThreshold);
		// maximum size that will be stored in memory
		// the location for saving data that is larger than getSizeThreshold()

		factory.setRepository(new File(repoPath));
		upload = new ServletFileUpload(factory);
		upload.setSizeMax(sizeMax);
		upload.setHeaderEncoding("UTF-8");

		setInit(true);
	}

	public long getSizeMax() {
		return upload.getSizeMax();
	}

	public int getSizeThreshold() {
		return factory.getSizeThreshold();
	}

	public boolean getAutomatic() {
		return automatic;
	}

	public String getRepository() {
		return factory.getRepository().getAbsolutePath();
	}

	public void parseRequest(HttpServletRequest req, ParameterParser params,
			String path) throws ServiceException {
		String contentType = req.getHeader(CONTENT_TYPE);
		if (!contentType.startsWith(MULTIPART_FORM_DATA)) {
			throw new ServiceException("the request doesn't contain a "
					+ MULTIPART_FORM_DATA + " stream");
		}
		int requestSize = req.getContentLength();
		if (requestSize == -1) {
			throw new ServiceException("the request was rejected because "
					+ "it's size is unknown");
		}
		if (requestSize > getSizeMax()) {
			throw new ServiceException("the request was rejected because "
					+ "it's size exceeds allowed range");
		}

		try {
			List<?> fileList = upload.parseRequest(req);
			if (fileList != null) {
				for (Iterator<?> it = fileList.iterator(); it.hasNext();) {
					DiskFileItem fi = (DiskFileItem) it.next();
					if (fi.isFormField()) {
						log.debug("Found an simple form field: "
								+ fi.getFieldName() + ", adding value "
								+ fi.getString());
						String value = null;
						try {
							value = fi.getString(params.getCharacterEncoding());
						} catch (UnsupportedEncodingException e) {
							log
									.error(params.getCharacterEncoding()
											+ " encoding is not supported."
											+ "Used the default when reading form data.");
							value = fi.getString();
						}
						params.append(fi.getFieldName(), value);
					} else {
						log.debug("Found an uploaded file: "
								+ fi.getFieldName());
						log
								.debug("It has " + fi.getSize()
										+ " Bytes and is "
										+ (fi.isInMemory() ? "" : "not ")
										+ "in Memory");
						log.debug("Adding FileItem as " + fi.getFieldName()
								+ " to the params");
						if (fi != null && StringUtil.isNotBlank(fi.getName())) {
							params.append(fi.getFieldName(), fi);
						}
					}
				}
			}
		} catch (FileUploadException e) {
			throw new ServiceException(e);
		}
	}
}
