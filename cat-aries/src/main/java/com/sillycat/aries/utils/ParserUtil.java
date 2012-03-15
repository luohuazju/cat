package com.sillycat.aries.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ParserUtil {
	 /** Property for setting the URL folding value */
    public static final String URL_CASE_FOLDING_KEY = "url.case.folding";

    /** No folding */
    public static final String URL_CASE_FOLDING_NONE_VALUE = "none";

    /** Fold to lower case */
    public static final String URL_CASE_FOLDING_LOWER_VALUE = "lower";

    /** Fold to upper case */
    public static final String URL_CASE_FOLDING_UPPER_VALUE = "upper";

    /** No folding set */
    private static final int URL_CASE_FOLDING_UNSET = 0;

    /** Folding set to "no folding" */
    public static final int URL_CASE_FOLDING_NONE = 1;

    /** Folding set to "lowercase" */
    public static final int URL_CASE_FOLDING_LOWER = 2;

    /** Folding set to "uppercase" */
    public static final int URL_CASE_FOLDING_UPPER = 3;

    /** Logging */
    private static Log log = LogFactory.getLog(ParserUtil.class);

    /** The folding from the properties */
    private static int folding = URL_CASE_FOLDING_UNSET;

    /**
     * Convert a String value according to the url.case.folding property.
     * 
     * @param value
     *            the String to convert
     * 
     * @return a new String.
     *  
     */
    public static String convertAndTrim(String value) {
        return convertAndTrim(value, getUrlFolding());
    }

    /**
     * A static version of the convert method, which trims the string data and
     * applies the conversion specified in the property given by
     * URL_CASE_FOLDING. It returns a new string so that it does not destroy the
     * value data.
     * 
     * @param value
     *            A String to be processed.
     * @return A new String converted to lowercase and trimmed.
     */
    public static String convertAndTrim(String value, int fold) {
        String tmp = value.trim();

        switch (fold) {
        case URL_CASE_FOLDING_NONE: {
            break;
        }
        case URL_CASE_FOLDING_LOWER: {
            tmp = tmp.toLowerCase();
            break;
        }
        case URL_CASE_FOLDING_UPPER: {
            tmp = tmp.toUpperCase();
            break;
        }
        default: {
            log.error("Passed " + fold + " as fold rule, which is illegal!");
            break;
        }
        }
        return tmp;
    }

    /**
     * Gets the folding value from the properties
     * 
     * @return The current Folding Value
     */
    public static int getUrlFolding() {
        if (folding == URL_CASE_FOLDING_UNSET) {
            /*Configuration conf = TurbineServices.getInstance()
                    .getConfiguration();
            String foldString = conf.getString(URL_CASE_FOLDING_KEY,
                    URL_CASE_FOLDING_NONE_VALUE).toLowerCase();*/
            String foldString = URL_CASE_FOLDING_NONE_VALUE;
            folding = URL_CASE_FOLDING_NONE;

            log.debug("Setting folding from " + foldString);
            if (StringUtils.isNotEmpty(foldString)) {
                if (foldString.equals(URL_CASE_FOLDING_NONE_VALUE)) {
                    folding = URL_CASE_FOLDING_NONE;
                } else if (foldString.equals(URL_CASE_FOLDING_LOWER_VALUE)) {
                    folding = URL_CASE_FOLDING_LOWER;
                } else if (foldString.equals(URL_CASE_FOLDING_UPPER_VALUE)) {
                    folding = URL_CASE_FOLDING_UPPER;
                } else {
                    log.error("Got " + foldString + " from "
                            + URL_CASE_FOLDING_KEY
                            + " property, which is illegal!");
                }
            }
        }
        return folding;
    }
}
