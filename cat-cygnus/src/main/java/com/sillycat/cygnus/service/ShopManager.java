package com.sillycat.cygnus.service;

import java.util.List;

import com.sillycat.cygnus.model.Shop;

public interface ShopManager {

	public List<Shop> getAll();

	public Shop get(String id);

	public void save(Shop shop);

}
