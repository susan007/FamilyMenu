package com.gree.bean.menu_search_byid;

public class SearchByIdResult {

	
	private String resultcode;
	private String reason;
	private IdData result;
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
	public IdData getResult() {
		return result;
	}
	public void setResult(IdData result) {
		this.result = result;
	}
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
}
