package com.gree.bean.menu_search_byname;

import java.io.Serializable;

/**
 * 具体的步骤及图片展示
 * @author susan
 *
 */
public class MStep implements Serializable{
	/**图片地址******/
	private String img; 
	/**对该图的描述******/
	private String step;
	
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

}
