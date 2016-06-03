package com.hong.bean.bySort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by susan on 16-6-1.
 */
public class MenuSortDegital implements Serializable{

    private String parentId;
    private String name;

    public List<MenuSortDegitalStep> getList() {
        return list;
    }

    public void setList(List<MenuSortDegitalStep> list) {
        this.list = list;
    }

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

    private List<MenuSortDegitalStep> list;


}
