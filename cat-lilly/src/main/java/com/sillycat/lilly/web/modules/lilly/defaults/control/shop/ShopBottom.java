package com.sillycat.lilly.web.modules.lilly.defaults.control.shop;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.modules.control.BaseControl;
import com.sillycat.corvus.web.velocity.VelocityContext;
import com.sillycat.cygnus.model.Shop;
import com.sillycat.cygnus.service.ShopManager;

public class ShopBottom extends BaseControl {

	protected Log log = LogFactory.getLog(this.getClass());

	public void doPerform(RunData runData, VelocityContext context)
			throws Exception {
		ShopManager shopManager = (ShopManager) this.getBean("shopManager");
		List<Shop> list = shopManager.getAll();
		if (list != null && !list.isEmpty()) {
			log.debug("there are many shops:" + list.size());
		}
		context.put("shopName", "Lilly");
	}

}
