package com.sillycat.corvus.web.modules;

import java.util.Hashtable;

import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.DispatcherController;
import com.sillycat.corvus.web.velocity.VelocityContext;

public abstract class GenericLoader extends Hashtable<String, Object> {

	private static final long serialVersionUID = 3790729190637580162L;

	private boolean reload = true;

	private boolean isCaching = true;

	public GenericLoader() {
		super();
	}

	public GenericLoader(int i) {
		super(i);
	}

	public boolean cache() {
		return this.isCaching;
	}

	public abstract void exec(RunData rundata, VelocityContext context,
			DispatcherController servlet) throws Exception;

	public boolean reload() {
		return this.reload;
	}

	public GenericLoader setReload(boolean reload) {
		this.reload = reload;
		return this;
	}
	
}
