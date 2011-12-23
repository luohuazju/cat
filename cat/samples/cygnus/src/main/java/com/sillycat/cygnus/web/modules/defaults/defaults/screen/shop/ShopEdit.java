package com.sillycat.cygnus.web.modules.defaults.defaults.screen.shop;

import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.modules.screen.BaseScreen;
import com.sillycat.corvus.web.velocity.VelocityContext;
import com.sillycat.cygnus.service.ShopManager;

public class ShopEdit extends BaseScreen {

	public void doPerform(RunData runData, VelocityContext context)
			throws Exception {
		String id = runData.getParameterParser().get("id");
		ShopManager shopManager = (ShopManager) this.getBean("shopManager");
		if (StringUtil.isNotBlank(id)) {
			context.put("shop", shopManager.get(id));
		}
	}

}
