package ru.azure.launcher.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Servers {

	@SerializedName("color")
	@Expose
	private String color;

	@SerializedName("name")
	@Expose
	private String name;

	@SerializedName("online")
	@Expose
	private int online;

	@SerializedName("maxonline")
	@Expose
	private int maxonline;

	@SerializedName("recommend")
	@Expose
	private boolean recommend;

	@SerializedName("host")
	@Expose
	private String host;

	@SerializedName("port")
	@Expose
	private int port;

	@SerializedName("id")
	@Expose
	private int id;
	@SerializedName("test")
	@Expose
	private boolean test;

	public Servers(String color, String name, int online, int maxonline, boolean recommend, String host, int port, int id, boolean test) {
		this.color = color;
		this.name = name;
		this.online = online;
		this.maxonline = maxonline;
		this.recommend = recommend;
		this.host = host;
		this.port = port;
		this.id = id;
		this.test = test;
	}
	 
	public String getname() {
		return name;
	}

    public String getColor() {
		return color;
	}
	
	public int getOnline(){
		return online;
	}

	public int getmaxOnline(){
		return maxonline;
	}

	public boolean getRecommend(){
		return recommend;
	}

	public String getHost() {
		return host;
	}

	public int getPort(){
		return port;
	}

	public int getId(){
		return id;
	}
	public boolean getTest(){
		return test;
	}
}