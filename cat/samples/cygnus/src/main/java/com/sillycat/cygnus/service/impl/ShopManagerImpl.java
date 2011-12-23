package com.sillycat.cygnus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sillycat.cygnus.model.Shop;
import com.sillycat.cygnus.service.ShopManager;

@Service("shopManager")
public class ShopManagerImpl implements ShopManager {
	
	public Log log = LogFactory.getLog(this.getClass());
	
	public void save(Shop shop){
		log.info("save object:" + shop);
	}

	public Shop get(String id) {
		Shop shop = new Shop();
		shop.setCreateDate(new Date());
		shop.setShopDescription("we sell java codes in this shop");
		shop.setShopId("9527");
		shop.setShopName("9527 Java Code Shop");
		shop.setShopType("soft");
		return shop;
	}

	public List<Shop> getAll() {
		List<Shop> list = new ArrayList<Shop>();
		Shop shop1 = new Shop();
		shop1.setCreateDate(new Date());
		shop1.setShopDescription("we sell java codes in this shop");
		shop1.setShopId("9527");
		shop1.setShopName("9527 Java Code Shop");
		shop1.setShopType("soft");

		Shop shop2 = new Shop();
		shop2.setCreateDate(new Date());
		shop2.setShopDescription("we sell clothes in this shop");
		shop2.setShopId("9528");
		shop2.setShopName("Kiko's Fashine Show");
		shop2.setShopType("clothes");

		list.add(shop1);
		list.add(shop2);
		return list;
	}

}
