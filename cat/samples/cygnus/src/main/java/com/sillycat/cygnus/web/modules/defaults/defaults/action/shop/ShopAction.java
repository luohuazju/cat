package com.sillycat.cygnus.web.modules.defaults.defaults.action.shop;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.modules.action.BaseAction;
import com.sillycat.corvus.web.velocity.VelocityContext;
import com.sillycat.cygnus.model.Shop;
import com.sillycat.cygnus.service.ShopManager;

public class ShopAction extends BaseAction {

	private static Log log = LogFactory.getLog(ShopAction.class);

	public void doPerform(RunData runData, VelocityContext context)
			throws Exception {
		log.debug("default!");
	}

	public void doEcho(RunData runData, VelocityContext context)
			throws IOException {
		String id = runData.getParameterParser().get("id");
		String shopName = runData.getParameterParser().get("shopName");
		String shopType = runData.getParameterParser().get("shopType");
		ShopManager shopManager = (ShopManager) this.getBean("shopManager");
		log.debug("id=" + id + " shopName=" + shopName + " shopType="
				+ shopType + " shopManager=" + shopManager);
		String redirect = "";
		redirect = "../shop/shopList.cat";
		runData.getResponse().sendRedirect(redirect);
	}

	public void doSave(RunData runData, VelocityContext context)
			throws Exception {
		String id = runData.getParameterParser().get("id");
		String shopName = runData.getParameterParser().get("shopName");
		String shopType = runData.getParameterParser().get("shopType");
		ShopManager shopManager = (ShopManager) this.getBean("shopManager");
		String redirect = "";
		Shop shop = null;
		if (StringUtil.isNotBlank(id)) {
			// update
			shop = shopManager.get(id);
			shop.setShopName(shopName);
			shop.setShopType(shopType);
			shopManager.save(shop);
			redirect = "../shop/shopEdit.cat?id=" + id;
		} else {
			// create
			shop = new Shop();
			shop.setShopName(shopName);
			shop.setShopType(shopType);
			shopManager.save(shop);
			redirect = "../shop/shopList.cat";
		}
		runData.getResponse().sendRedirect(redirect);
	}

}
