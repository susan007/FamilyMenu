package com.gree.bean.menu_search_byname;

import java.io.Serializable;


public class MenuResult implements Serializable{
	/***返回结果码********/
	private String  resultcode="";
	/***返回reason********/
	private String reason="";
	/***具体菜谱********/
	private MData result;
	/***返回错误码********/
	private String error_code;
	public String getResultcode() {
		return resultcode;
	}
	public void setResultcode(String resultcode) {
		this.resultcode = resultcode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public MData getResult() {
		return result;
	}
	public void setResult(MData result) {
		this.result = result;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

}
