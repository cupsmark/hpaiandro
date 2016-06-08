package com.hpai.models;

public class MGuessTestimoni {

	private int id;
	private String title;
	private String name;
	private String desc;
	private String image_url;
	private String cat;
	
	public MGuessTestimoni()
	{
		
	}
	public MGuessTestimoni(int id,String title,String name,String desc,String image_url)
	{
		this.id = id;
		this.title = title;
		this.name = name;
		this.desc = desc;
		this.image_url = image_url;
	}
	
	public MGuessTestimoni(int id,String title,String name,String desc,String image_url,String cat)
	{
		this.id = id;
		this.title = title;
		this.name = name;
		this.desc = desc;
		this.image_url = image_url;
		this.cat = cat;
	}
	
	
	public int getId()
	{
		return this.id;
	}
	public String getTitle()
	{
		return this.title;
	}
	public String getName()
	{
		return this.name;
	}
	public String getDesc()
	{
		return this.desc;
	}
	public String getImageUrl()
	{
		return this.image_url;
	}
	public String getCat()
	{
		return this.cat;
	}
}
