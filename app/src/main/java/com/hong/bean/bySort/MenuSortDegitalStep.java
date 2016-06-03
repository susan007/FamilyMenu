package com.hong.bean.bySort;

import java.io.Serializable;

/**
 * Created by susan on 16-6-1.
 */
public class MenuSortDegitalStep implements Serializable {
    private String id;
    private String name;
    private String parentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


}
