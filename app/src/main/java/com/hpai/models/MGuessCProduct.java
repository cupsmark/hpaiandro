package com.hpai.models;

public class MGuessCProduct {

	private String id ;
	private String title;
	private String img_url;
	public MGuessCProduct()
	{
		
	}
	
	public MGuessCProduct(String id,String title,String img_url)
	{
		this.id = id;
		this.title = title;
		this.img_url = img_url;
	}
	
	public String getId()
	{
		return this.id;
	}
	public String getTitle()
	{
		return this.title;
	}
	public String getImgUrl()
	{
		return this.img_url;
	}
}
