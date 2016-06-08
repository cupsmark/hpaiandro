package com.hpai.models;

public class MComment {

	private String comment_id;
	private String status_id;
	private String uid;
	private String comment_text;
	private String comment_date;
	
	
	
	public MComment(String id,String status_id,String uid,String text,String date)
	{
		this.comment_id = id;
		this.status_id = status_id;
		this.uid = uid;
		this.comment_text = text;
		this.comment_date = date;
	}
	
	public void setCommentID(String id)
	{
		this.comment_id = id;
	}
	
	public String getCommentID()
	{
		return this.comment_id;
	}
	
	public void setStatusID(String status_id)
	{
		this.status_id = status_id;
	}
	
	public String getStatusID()
	{
		return this.status_id;
	}
	
	public void setUID(String uid)
	{
		this.uid = uid;
	}
	
	public String getUID()
	{
		return this.uid;
	}
	
	public void setCommentText(String text)
	{
		this.comment_text = text;
	}
	
	public String getCommentText()
	{
		return this.comment_text;
	}
	
	public void setCommentDate(String date)
	{
		this.comment_date = date;
	}
	
	public String getCommentDate()
	{
		return this.comment_date;
	}
	
}
