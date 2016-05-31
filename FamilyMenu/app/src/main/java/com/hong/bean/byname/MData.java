package com.hong.bean.byname;

import java.io.Serializable;
import java.util.List;

import android.R.integer;

/**
 * 展示具体的菜谱详情
 * @author susan
 *
 */
public class MData implements Serializable{
	/***具体的菜谱*******/
	private List<MenuDigital> data;
	/***总数？*******/
	private String totalNum;
	/***	数据返回起始下标*******/
	private String pn;
	/***数据返回条数，最大30*******/
	private String rn;
	public List<MenuDigital> getData() {
		return data;
	}
	public void setData(List<MenuDigital> data) {
		this.data = data;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getPn() {
		return pn;
	}
	public void setPn(String pn) {
		this.pn = pn;
	}
	public String getRn() {
		return rn;
	}
	public void setRn(String rn) {
		this.rn = rn;
	}

}
