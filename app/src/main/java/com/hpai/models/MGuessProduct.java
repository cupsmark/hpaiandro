package com.hpai.models;

public class MGuessProduct {
	
	private int id;
	private String title;
	private String desc;
	private String image_url;
	private String cat;
	
	public MGuessProduct()
	{
		
	}
	
	public MGuessProduct(int id,String title,String desc,String image)
	{
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.image_url = image;
	}
	public MGuessProduct(int id,String title,String desc,String image,String cat)
	{
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.image_url = image;
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
	public String getDesc()
	{
		return this.desc;
	}
	public String getImageUri()
	{
		return this.image_url;
	}
	public String getCat()
	{
		return this.cat;
	}

}
