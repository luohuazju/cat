package com.sillycat.corvus.web.modules.screen;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.helper.JavaClassHelper;
import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.DispatcherController;
import com.sillycat.corvus.web.modules.AssemblerFactory;
import com.sillycat.corvus.web.modules.GenericLoader;
import com.sillycat.corvus.web.velocity.VelocityContext;

public class ScreenLoader extends GenericLoader {

	private static final long serialVersionUID = 8075178471663014214L;

	private static Log log = LogFactory.getLog(ScreenLoader.class);

	private static ScreenLoader instance = new ScreenLoader(50);

	private static AssemblerFactory javaScreenFactory = new JavaScreenFactory();

	private ScreenLoader() {
		super();
	}

	private ScreenLoader(int i) {
		super(i);
	}

	private void addInstance(String name, Screen screen) {
		if (cache()) {
			this.put(name, (Screen) screen);
		}
	}

	public void exec(RunData rundata, VelocityContext context,
			DispatcherController servlet) throws Exception {
		String screen = rundata.getRequest().getServletPath();
		if (StringUtil.isNotBlank(screen)) {
			screen = JavaClassHelper.getScreenClassName(screen);
			getScreen(screen, servlet).doPerform(rundata, context);
		}
	}

	/**
	 * 根据Screen类名获取类事例
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Screen getScreen(String name, DispatcherController servlet)
			throws Exception {
		Screen screen = null;

		// Check if the action is already in the cache
		if (cache() && this.containsKey(name)) {
			screen = (Screen) this.get(name);
			log.debug("Found Screen " + name + " in the cache!");
		} else {
			log.debug("Loading Screen " + name + " from the Assembler Broker");
			try {
				screen = (Screen) javaScreenFactory.getAssembler(name);
			} catch (ClassCastException cce) {
				log.error(cce);
				throw new Exception("找不到对应的SCREEN类");
			}
		}
		if (cache() && !this.containsKey(name)) {
			screen.setServlet(servlet);
			addInstance(name, screen);
		}
		return screen;
	}

	public static ScreenLoader getInstance() {
		return instance;
	}

}
