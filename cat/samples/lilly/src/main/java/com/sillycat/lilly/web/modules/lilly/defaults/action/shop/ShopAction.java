package com.sillycat.lilly.web.modules.lilly.defaults.action.shop;

import com.sillycat.aries.utils.StringUtil;
import com.sillycat.corvus.models.RunData;
import com.sillycat.corvus.web.velocity.VelocityContext;

public class ShopAction
		extends
		com.sillycat.cygnus.web.modules.defaults.defaults.action.shop.ShopAction {

	public void doSave(RunData runData, VelocityContext context)
			throws Exception {
		String id = runData.getParameterParser().get("id");
		String redirect = "";
		if (StringUtil.isNotBlank(id)) {
			// update
			redirect = "../shop/shopEdit.cat?id=" + id + "&shop=lilly";
		} else {
			// create
			redirect = "../shop/shopList.cat?&shop=lilly";
		}
		runData.getResponse().sendRedirect(redirect);
	}

}
