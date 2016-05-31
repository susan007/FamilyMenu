package com.ghong.tool;


import com.google.gson.Gson;
import com.hong.bean.byname.MData;
import com.hong.bean.byname.MenuResult;

public class ParseJsonobject {

	private Gson gson = new Gson();
	private MenuResult mr;
	private MData md;

	public MenuResult parseMenuResult2(String json) {
		mr=gson.fromJson(json, MenuResult.class);
		return mr;
	}


}
