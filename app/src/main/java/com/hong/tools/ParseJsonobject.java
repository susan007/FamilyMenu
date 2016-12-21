package com.hong.tools;


import com.google.gson.Gson;
import com.hong.bean.bySort.MenuSortResult;
import com.hong.bean.byname.MData;
import com.hong.bean.byname.MenuResult;

public class ParseJsonobject {

	private Gson gson = new Gson();
	private MenuResult mr;
	private MenuSortResult msr;
	private MData md;

	public MenuResult parseMenuResult2(String json) {
		mr=gson.fromJson(json, MenuResult.class);
		return mr;
	}

	public MenuSortResult parseMenuSortResult(String json){
		msr=gson.fromJson(json,MenuSortResult.class);
		return msr;
	}



}
