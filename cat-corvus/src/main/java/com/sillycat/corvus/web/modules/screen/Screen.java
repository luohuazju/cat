package com.sillycat.corvus.web.modules.screen;

import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.modules.Assembler;
import com.sillycat.corvus.web.velocity.VelocityContext;

public abstract class Screen extends Assembler {
	public abstract void doPerform(RunData rundata, VelocityContext context)
			throws Exception;
}
