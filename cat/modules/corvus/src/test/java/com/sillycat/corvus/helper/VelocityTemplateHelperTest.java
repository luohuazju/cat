package com.sillycat.corvus.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VelocityTemplateHelperTest {

	@Test
	public void getSceenTemplatePath() {
		VelocityTemplateHelper.init();
		assertEquals(VelocityTemplateHelper
				.getScreenTemplatePath("lilly/defaults/shop/blank.cat"),
				"defaults/defaults/template/screen/shop/blank.vm");
		assertEquals(VelocityTemplateHelper
				.getScreenTemplatePath("lilly/en/shop/blank.cat"),
				"defaults/defaults/template/screen/shop/blank.vm");
		assertEquals(VelocityTemplateHelper
				.getScreenTemplatePath("default/defaults/shop/blank.cat"),
				"defaults/defaults/template/screen/shop/blank.vm");
	}

	@Test
	public void getControlTemplateName() {
		VelocityTemplateHelper.init();
		assertEquals(VelocityTemplateHelper.getControlTemplateName("bottom.vm",
				"lilly/defaults/shop/blank.cat"),
				"lilly/defaults/template/control/shop/bottom.vm");
	}

	@Test
	public void getPageTemplatePath() {
		assertEquals(VelocityTemplateHelper
				.getPageTemplatePath("lilly/defaults/shop/blank.cat"),
				"defaults/defaults/template/page/shop/default.vm");
	}
}
