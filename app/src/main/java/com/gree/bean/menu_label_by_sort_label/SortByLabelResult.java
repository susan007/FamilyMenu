package com.gree.bean.menu_label_by_sort_label;

import java.util.List;

public class SortByLabelResult {
	
	private String resultcode;
	private String reason;
	private List<SortByLabelList> result;
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
	
	public String getError_code() {
		return error_code;
	}
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	public List<SortByLabelList> getResult() {
		return result;
	}
	public void setResult(List<SortByLabelList> result) {
		this.result = result;
	}

}
