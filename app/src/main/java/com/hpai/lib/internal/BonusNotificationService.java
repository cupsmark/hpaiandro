package com.hpai.lib.internal;

import java.util.List;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hpai.BonusReceiv;
import com.hpai.R;
import com.hpai.connection.URLConnection;
import com.hpai.dbhelper.DBConnection;
import com.hpai.models.MUsers;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import android.os.IBinder;


public class BonusNotificationService extends Service{

	final static String ACTION = "NotifyServiceAction";
	final static String STOP_SERVICE = "";
	final static int RQS_STOP_SERVICE = 1;
	
	//BonusNotificationReceiver receiver;
	//private static final int MY_NOTIFICATION_ID=1;
	private NotificationManager notifManager;
	private Notification myNotification;
	//private final String myBlog = "http://hpaindonesia.net/";
	String token = "";
	TimerTask doAsynchronousTask;
	String[] results;
	
	
	String[] ids;
	String[] titles;
	String[] descs;
	String[] images;
	String devId;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//receiver = new BonusNotificationReceiver();
		super.onCreate();
	}
	

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		token = getToken(getApplicationContext());
		//devId = getDeviceId(getApplicationContext());
		devId = GCMUtilities.deviceId(getApplicationContext());
		//if((intent != null && intent.getAction() != null)){
			//token = intent.getStringExtra("token");
			try {
            	
                new AsyncTask<String, Integer, String>(){

					@Override
					protected String doInBackground(String... params) {
						// TODO Auto-generated method stub
						String result = "";
						if(!URLConnection.checkConnection(getApplicationContext()))
						{
							result = "0";
						}
						else
						{
							String json = "";
							/*if(token.equals("")){
								json = URLConnection.getJsonString(URLConnection.URLNotify);
							}
							else
							{*/
								json = URLConnection.getJsonString(URLConnection.URLNotify+"?token="+token+"&did="+devId);
							//}
							
							
							if((json != null))
							{
								JSONObject obj;
								try {
									obj = new JSONObject(json);
									if(!obj.getString("results").equals("0"))
									{
										result = obj.getString("rows");
									}
									else
									{
										result = "0";
									}
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									result = "0";
									e.printStackTrace();
								}
							}
							else
							{
								result = "0";
							}
						}
						
						
						return result;
					}
                	
					protected void onPostExecute(String result) {
						if(!result.equals("0")){
							send_notif(result);
						}
						
					};
                }.execute();
            } catch (Exception e) {
                // TODO Auto-generated catch block
            	AlertDialog.Builder adb = new AlertDialog.Builder(getApplicationContext());
        		adb.setMessage(e.getMessage().toString());
        		adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
    				
    				@Override
    				public void onClick(DialogInterface dialog, int which) {
    					// TODO Auto-generated method stub
    					
    				}
    			});
        		adb.create().show();
            }
		//}
		

		return START_STICKY;
	}
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//this.unregisterReceiver(receiver);
		//doAsynchronousTask.cancel();
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	private void send_notif(String json)
	{
		JSONArray arr;
		
		int length = 0;
		try {
			arr = new JSONArray(json);
			ids = new String[arr.length()];
			titles = new String[arr.length()];
			descs = new String[arr.length()];
			images = new String[arr.length()];
			length = arr.length();
			for(int i = 0;i < arr.length();i++)
			{
				JSONObject obj2 = arr.getJSONObject(i);
				ids[i] = obj2.getString("id");
				titles[i] = obj2.getString("t");
				descs[i] = obj2.getString("d");
				images[i] = obj2.getString("img");
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		String longLength = "Ada " + Integer.toString(length)+ " notifikasi";
		notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		if(length > 1)
		{
			myNotification = new Notification(R.drawable.ic_launcher,"Notifikasi HPAI Mobile",System.currentTimeMillis());
		}
		else
		{
			myNotification = new Notification(R.drawable.ic_launcher,titles[length - 1],System.currentTimeMillis());
		}
		
		
		Context context = getApplicationContext();
		//String title = obj2.getString("t");
		//String desc = obj2.getString("d");
		Intent myIntent = new Intent(context,BonusReceiv.class);
		myIntent.putExtra("title", titles);
		myIntent.putExtra("desc", descs);
		myIntent.putExtra("id", ids);
		myIntent.putExtra("image", images);
		myIntent.putExtra("token", token);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		myNotification.defaults |= Notification.DEFAULT_SOUND;
		myNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		myNotification.icon |= R.drawable.ic_launcher;
		if(length > 1)
		{
			myNotification.setLatestEventInfo(context, "Notifikasi HPAI Mobile", longLength, pendingIntent);
		}
		else
		{
			myNotification.setLatestEventInfo(context, titles[length-1], descs[length - 1], pendingIntent);
		}
		
		notifManager.notify(989898,myNotification);
		
		//Toast.makeText(this, "running", Toast.LENGTH_LONG).show();
	}
	
	private String getToken(Context context)
	{
		String token = "";
		DBConnection conn = new DBConnection(context);
		List<MUsers> u = conn.userIsLogin();
		if(u.size() > 0)
		{
			for(MUsers us : u)
			{
				token = us.getToken();
			} 	
		}
		else
		{
			token = "";
		}
		return token;
	}
	
	
}
