package ru.azure.launcher.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {

	@SerializedName("imageurl")
	@Expose
	private String imageurl;

	@SerializedName("title")
	@Expose
	private String title;

	@SerializedName("titleBig")
	@Expose
	private String titleBig;

	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("imageFullUrl")
	@Expose
	private String imageFullUrl;

	public News (String imageurl, String title, String titleBig, String url, String imageFullUrl) {
		this.imageurl = imageurl;
		this.title = title;
		this.titleBig = titleBig;
		this.url = url;
		this.imageFullUrl = imageFullUrl;
	}

	public String getImageUrl() {
		return imageurl;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleBig() {
		return titleBig;
	}

	public String getUrl() {
		return url;
	}

	public String getImageFullUrl() {
		return imageFullUrl;
	}

}