package com.gree.bean.menu_search_byname;

import java.io.Serializable;
import java.util.List;

public class MenuDigital implements Serializable{
	/***菜名的id************/
	private String id;
	/***菜名************/
	private String title;
	/***菜的标签************/
	private String tags;
	/***对这道菜的描述************/
	private String imtro;
	/***做菜所需材料以及重量************/
	private String ingredients;
	/***做菜所需调料************/
	private String burden;
	/***这道菜的成品图？************/
	private List<String> albums;
	/***具体的步骤************/
	private List<MStep> steps;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getImtro() {
		return imtro;
	}
	public void setImtro(String imtro) {
		this.imtro = imtro;
	}
	public String getIngredients() {
		return ingredients;
	}
	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}
	public String getBurden() {
		return burden;
	}
	public void setBurden(String burden) {
		this.burden = burden;
	}
	public List<String> getAlbums() {
		return albums;
	}
	public void setAlbums(List<String> albums) {
		this.albums = albums;
	}
	public List<MStep> getSteps() {
		return steps;
	}
	public void setSteps(List<MStep> steps) {
		this.steps = steps;
	}

}
