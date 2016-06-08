package com.hpai.models;

public class MUsers {

	private String user_id;
	private String name;
	private String token;
	private String status_login;
	private String time;
	private String registration_id;
	private String interval;
	private String avatar;
	
	public MUsers(String userid,String name,String token,String status,String time)
	{
		this.user_id = userid;
		this.name = name;
		this.token = token;
		this.status_login = status;
		this.time = time;
	}
	
	public MUsers(String userid,String name,String token,String status,String time,String devId)
	{
		this.user_id = userid;
		this.name = name;
		this.token = token;
		this.status_login = status;
		this.time = time;
		this.registration_id = devId;
	}
	
	public MUsers(String userid,String name,String token,String status,String time,String devId,String interval,String avatar)
	{
		this.user_id = userid;
		this.name = name;
		this.token = token;
		this.status_login = status;
		this.time = time;
		this.registration_id = devId;
		this.interval = interval;
		this.avatar = avatar;
	}
	
	public String getUID()
	{
		return this.user_id;
	}
	public void setUID(String uid)
	{
		this.user_id = uid;
	}
	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getToken()
	{
		return this.token;
	}
	public void setToken(String token)
	{
		this.token = token;
	}
	public String getStatus()
	{
		return this.status_login;
	}
	public void setStatus(String status)
	{
		this.status_login = status;
	}
	public String getTime()
	{
		return this.time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public String getRegID()
	{
		return this.registration_id;
	}
	public void setRegID(String regid)
	{
		this.registration_id = regid;
	}
	public String getInterval()
	{
		return this.interval;
	}
	public void setInterval(String interval)
	{
		this.interval = interval;
	}
	public String getAvatar()
	{
		return this.avatar;
	}
	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}
}
