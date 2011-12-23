package com.sillycat.corvus.web.modules.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.helper.JavaClassHelper;
import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.DispatcherController;
import com.sillycat.corvus.web.modules.AssemblerFactory;
import com.sillycat.corvus.web.modules.GenericLoader;
import com.sillycat.corvus.web.velocity.VelocityContext;

public class ActionLoader extends GenericLoader {

	private static final long serialVersionUID = 8075178471663014211L;

	private static Log log = LogFactory.getLog(ActionLoader.class);

	private static ActionLoader instance = new ActionLoader(50);

	/** The Assembler Broker Service */
	private static AssemblerFactory javaActionFactory = new JavaActionFactory();

	private ActionLoader() {
		super();
	}

	private ActionLoader(int i) {
		super(i);
	}

	private void addInstance(String name, Action action) {
		if (cache()) {
			this.put(name, (Action) action);
		}
	}

	public void exec(RunData rundata, VelocityContext context,
			DispatcherController servlet) throws Exception {
		String actionName = rundata.getAction();
		String servletPath = rundata.getRequest().getServletPath();
		if (StringUtil.isNotBlank(actionName)) {
			actionName = JavaClassHelper.getActionClassName(actionName,
					servletPath);
			Action action = getAction(actionName, servlet);
			action.setServlet(servlet);
			if (action instanceof ActionEvent) {
				if (StringUtil.isNotBlank(rundata.getEvent())) {
					((ActionEvent) action).execute(rundata, context);
				} else {
					action.doPerform(rundata, context);
				}
			} else {
				action.doPerform(rundata, context);
			}
		}
	}

	/**
	 * 根据action类名获取类事例
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Action getAction(String name, DispatcherController servlet)
			throws Exception {
		Action action = null;
		if (cache() && this.containsKey(name)) {
			action = (Action) this.get(name);
			log.debug("Found Action " + name + " in the cache!");
		} else {
			log.debug("Loading Action " + name + " from the Assembler Broker");
			try {
				action = (Action) javaActionFactory.getAssembler(name);
			} catch (ClassCastException cce) {
				log.error(cce);
				throw new Exception("找不到对应的ACTION类");
			}
		}
		if (cache() && !this.containsKey(name)) {
			action.setServlet(servlet);
			addInstance(name, action);
		}
		return action;
	}

	public static ActionLoader getInstance() {
		return instance;
	}

}
