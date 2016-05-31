package com.gree.bean.menu_search_byid;

import java.util.List;

public class IdData {

	
	private String totalNum;
	private String pn;
	private String rn;
	private List<IdDegital> data;
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
	public List<IdDegital> getData() {
		return data;
	}
	public void setData(List<IdDegital> data) {
		this.data = data;
	}
	
}
