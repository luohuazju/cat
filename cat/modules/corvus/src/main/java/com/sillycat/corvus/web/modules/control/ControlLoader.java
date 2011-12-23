package com.sillycat.corvus.web.modules.control;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.DispatcherController;
import com.sillycat.corvus.web.modules.AssemblerFactory;
import com.sillycat.corvus.web.modules.GenericLoader;
import com.sillycat.corvus.web.velocity.VelocityContext;

public class ControlLoader extends GenericLoader {

	private static final long serialVersionUID = 8075178471663014213L;

	private static Log log = LogFactory.getLog(ControlLoader.class);

	private static ControlLoader instance = new ControlLoader(50);

	/** The Assembler Broker Service */
	private static AssemblerFactory javaControlFactory = new JavaControlFactory();

	private ControlLoader() {
		super();
	}

	private ControlLoader(int i) {
		super(i);
	}

	private void addInstance(String name, Control control) {
		if (cache()) {
			this.put(name, control);
		}
	}

	/**
	 * 根据Control类名获取类事例,但control可以不存在对应的类
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Control getControl(String name, DispatcherController servlet)
			throws Exception {
		Control control = null;
		if (cache() && containsKey(name)) {
			control = (Control) get(name);
			log.debug("Found Control " + name + " in the cache!");
		} else {
			log.debug("Loading Control " + name + " from the Assembler Broker");
			try {
				control = (Control) javaControlFactory.getAssembler(name);
			} catch (ClassCastException e) {
				log.error(e);
				throw new Exception("can't not find control class:" + name);
			}
		}
		if (cache() && !this.containsKey(name)) {
			control.setServlet(servlet);
			addInstance(name, control);
		}
		return control;
	}

	public static ControlLoader getInstance() {
		return instance;
	}

	public void exec(RunData rundata, VelocityContext context,
			DispatcherController servlet) throws Exception {
		// useless
	}

}
