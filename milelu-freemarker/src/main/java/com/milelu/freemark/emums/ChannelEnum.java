package com.milelu.freemark.emums;

import lombok.Getter;

/**
 * @author Laity
 */

public enum ChannelEnum {
	/**
	 * 内容通道
	 */
	CONTENT("CONTENT","内容通道"),
	/**
	 * 内容通道
	 */
	CONTENTS("CONTENTS","内容通道"),
	/**
	 * 分类通道
	 */
	CATEGORY("CATEGORY","分类通道"),
	/**
	 * 分类分页通道
	 */
	CATEGORY_PAGE("CATEGORY_PAGE","分类分页通道"),
	/**
	 * 模板通道
	 */
	TEMPLATE("TEMPLATE","模板通道"),
	/**
	 * 首页通道
	 */
	HOME("HOME","首页通道");


	@Getter
	private String code;

	@Getter
	private String name;

	ChannelEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
