package com.gree.tools;

import com.gree.bean.menu_search_byname.MenuResult;

public class MyApplication {
	public  static MenuResult mr;

	public static MenuResult getMr() {
		return mr;
	}

	public static void setMr(MenuResult mr) {
		MyApplication.mr = mr;
	}

	
}
