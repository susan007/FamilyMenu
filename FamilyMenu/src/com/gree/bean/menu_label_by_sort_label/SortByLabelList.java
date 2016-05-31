package com.gree.bean.menu_label_by_sort_label;

import java.util.List;

public class SortByLabelList {
	private String parentId;
	private String name;
	private List<SortByLabelDegital> list;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SortByLabelDegital> getList() {
		return list;
	}

	public void setList(List<SortByLabelDegital> list) {
		this.list = list;
	}

}
