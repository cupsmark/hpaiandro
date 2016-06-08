package com.hpai.models;

public class MStatus {

	private String status_id;
	private String uid;
	private String status_text;
	private String status_time;
	private String status_flag;
	private String status_uname;
	private String status_pict;
	
	
	public MStatus(String id,String uid,String text,String time,String flag,String uname,String pict)
	{
		this.status_id = id;
		this.uid = uid;
		this.status_text = text;
		this.status_time = time;
		this.status_flag = flag;
		this.status_uname = uname;
		this.status_pict = pict;
	}
	
	public void setStatusID(String id)
	{
		this.status_id = id;
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
	
	public void setStatusText(String text)
	{
		this.status_text = text;
	}
	
	public String getStatusText()
	{
		return this.status_text;
	}
	
	public void setStatusTime(String time)
	{
		this.status_time = time;
	}
	
	public String getStatusTime()
	{
		return this.status_time;
	}
	
	public void setStatusFlag(String flag)
	{
		this.status_flag = flag;
	}
	
	public String getStatusFlag()
	{
		return this.status_flag;
	}
	
	public void setStatusUname(String uname)
	{
		this.status_uname = uname;
	}
	
	public String getStatusUname()
	{
		return this.status_uname;
	}
	
	public void setStatusPict(String pict)
	{
		this.status_pict = pict;
	}
	
	public String getStatusPict()
	{
		return this.status_pict;
	}
}
