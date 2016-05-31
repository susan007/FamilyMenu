package com.gree.bean.menu_search_byid;

import java.util.List;

public class IdDegital {

	
	private String id;
	private String title;
	private String tags;
	private String mitro;
	private String ingredients;
	private String burden;
	private List<String> albums;
	private List<IdStep> steps;
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
	public String getMitro() {
		return mitro;
	}
	public void setMitro(String mitro) {
		this.mitro = mitro;
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
	public List<IdStep> getSteps() {
		return steps;
	}
	public void setSteps(List<IdStep> steps) {
		this.steps = steps;
	}
	
}
