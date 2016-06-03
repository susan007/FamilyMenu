package com.hong.bean.bySort;

import java.io.Serializable;
import java.util.List;

/**
 * Created by susan on 16-6-1.
 */
public class MenuSortResult implements Serializable {

    private String resultcode;
    private String reason;
    public List<MenuSortDegital> result;
    public String getError_code;

    public String getGetError_code() {
        return getError_code;
    }

    public void setGetError_code(String getError_code) {
        this.getError_code = getError_code;
    }

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

    public List<MenuSortDegital> getResult() {
        return result;
    }

    public void setResult(List<MenuSortDegital> result) {
        this.result = result;
    }


}
