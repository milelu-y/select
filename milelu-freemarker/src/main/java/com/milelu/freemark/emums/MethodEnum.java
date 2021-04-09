package com.milelu.freemark.emums;

import lombok.Getter;

public enum MethodEnum {

	FORMAT("时间格式化","format"),

	CLEAR_HTML("html格式化","clearHtml"),

	SUB("字符串截取","sub"),

	IMPORT("页面片段导入","import"),

	GLOBAL("全局变量","global"),
   ;


	@Getter
	private String name;
	@Getter
	private String value;

	MethodEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}
}
