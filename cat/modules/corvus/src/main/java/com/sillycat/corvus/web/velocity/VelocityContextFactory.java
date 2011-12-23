package com.sillycat.corvus.web.velocity;

import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.DispatcherController;
import com.sillycat.corvus.web.modules.TemplateControl;

public class VelocityContextFactory {

	public static VelocityContext getContext(RunData runData,
			DispatcherController servlet) {
		VelocityContext context = new VelocityContext();
		TemplateControl control = new TemplateControl(runData, servlet);
		context.put("control", control);
		context.put("rundata", runData);
		return context;
	}

}
