package com.hpai.models;

public class MGuessCTestimoni {

	private int id;
	private String title;
	private String desc;
	private String image_url;
	public MGuessCTestimoni()
	{
		
	}
	
	public MGuessCTestimoni(int id,String title,String desc,String image_url)
	{
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.image_url = image_url;
	}
	
	public int getId()
	{
		return this.id;
	}
	public String getTitle()
	{
		return this.title;
	}
	public String getDesc()
	{
		return this.desc;
	}
	public String getImageUrl()
	{
		return this.image_url;
	}
}
