package com.hpai.models;

public class MGuessInfo {

	private int id;
	private String title;
	private String desc;
	private String image_url;
	private String pdf_url;
	
	public MGuessInfo()
	{
		
	}
	public MGuessInfo(int id,String title,String desc,String image_url)
	{
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.image_url = image_url;
	}
	
	public MGuessInfo(int id,String title,String desc,String image_url,String pdf_url)
	{
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.image_url = image_url;
		this.pdf_url = pdf_url;
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
	public String getPDFUrl()
	{
		return this.pdf_url;
	}
}
