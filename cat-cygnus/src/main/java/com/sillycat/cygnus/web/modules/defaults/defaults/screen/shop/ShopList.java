package com.sillycat.cygnus.web.modules.defaults.defaults.screen.shop;

import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.modules.screen.BaseScreen;
import com.sillycat.corvus.web.velocity.VelocityContext;
import com.sillycat.cygnus.service.ShopManager;

public class ShopList extends BaseScreen {

	public void doPerform(RunData runData, VelocityContext context)
			throws Exception {
		ShopManager shopManager = (ShopManager) this.getBean("shopManager");
		context.put("shops", shopManager.getAll());
	}

}
