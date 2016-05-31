package com.gree.tools;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.gree.bean.menu_search_byname.MData;
import com.gree.bean.menu_search_byname.MenuResult;

public class ParseJsonobject {

	private Gson gson = new Gson();
	private MenuResult mr;
	private MData md;

	// 解析获取到的json字符串
	public Object parseMenuResult(String json, Class cls) {
		cls = gson.fromJson(json, cls);
		return cls;
	}

	public MenuResult parseMenuResult2(String json) {
		mr=gson.fromJson(json, MenuResult.class);
		return mr;
	}

	public MData parseMenuData(String json) {
		md = gson.fromJson(json, MData.class);
		return md;
	}

}
