package com.sillycat.corvus.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JavaClassHelperTest {

	@Test
	public void getScreenClassName() {
		assertEquals(JavaClassHelper
				.getScreenClassName("lilly/defaults/shop/shopList.cat"),
				"lilly.defaults.screen.shop.ShopList");
		assertEquals(JavaClassHelper
				.getScreenClassName("defaults/defaults/shop/shopList.cat"),
				"defaults.defaults.screen.shop.ShopList");
	}

	@Test
	public void getControlClassName() {
		assertEquals(JavaClassHelper.getControlClassName("shopBottom.vm",
				"lilly/defaults/shop/shopList.cat"),
				"lilly.defaults.control.shop.ShopBottom");
		assertEquals(JavaClassHelper.getControlClassName("shopBottom.vm",
				"defaults/defaults/shop/shopList.cat"),
				"defaults.defaults.control.shop.ShopBottom");
	}

	@Test
	public void getActionClassName() {
		assertEquals(JavaClassHelper.getActionClassName("shop/shopAction",
				"defaults/defaults/shop/shopEdit.cat?id=1"),
				"defaults.defaults.action.shop.ShopAction");
	}

}
