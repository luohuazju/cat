package com.sillycat.corvus.web.rundata;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sillycat.aries.exceptions.ServiceException;
import com.sillycat.corvus.models.RunData;

public class RundataFactory {

    public static RunData getRundata(HttpServletRequest request,
            HttpServletResponse response, ServletConfig config) throws ServiceException {
        RunData runData = new RunData(request, response, request.getSession(), config
                .getServletContext(), config);
        ParameterParser parameterParser = new DefaultParameterParser();
        parameterParser.setRequest(request);
        runData.setParameterParser(parameterParser);
        return runData;
    }
}
