package com.hpai.lib.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

public class GCMRegisterReceiver extends BroadcastReceiver{

	private static String KEY = "c2dmPref";
	private static String REGISTRATION_KEY = "registrationKey";
	private Context context;
	String reg = "";
	
	private NotificationManager notifManager;
	private Notification myNotification;
	String token = "";
	String[] ids;
	String[] titles;
	String[] descs;
	String[] images;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		this.context = context;
		try{
			if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
				handleRegistration(context, intent);
			}
			else if("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
				handleMessage(context, intent);
			}
		}
		catch(Exception ex){
			AlertDialog.Builder b = new AlertDialog.Builder(context);
			b.setMessage(ex.getMessage().toString());
			b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).create().show();
		}
	}
	
	private void handleRegistration(Context context,Intent intent)
	{
		String token = getToken();
		String regID = intent.getStringExtra("registration_id");
		reg = regID;
		if(regID != null){
			sendIDToServer(regID,token);
			Editor editor = context.getSharedPreferences(KEY, Context.MODE_PRIVATE).edit();
			editor.putString(REGISTRATION_KEY, regID);
			editor.commit();
		}
	}
	
	private void handleMessage(final Context context,final Intent intent)
	{
		//String message = intent.getStringExtra("message");
		try {
        	
            new AsyncTask<String, Integer, String>(){

				@Override
				protected String doInBackground(String... params) {
					// TODO Auto-generated method stub
					String result = "";
					
					if(!URLConnection.checkConnection(context.getApplicationContext()))
					{
						result = "0";
					}
					else
					{
						String message = intent.getStringExtra("message");
						String json = message;
						
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
					
					return result;
				}
            	
				protected void onPostExecute(String result) {
					if(!result.equals("0")){
						send_notif(result,context);
					}
					
				};
            }.execute();
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
	}
	
	private String getToken()
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
	
	public void sendIDToServer(String regID,String token)
    {
		HttpClient client = new DefaultHttpClient();
		  HttpPost post = new HttpPost("http://cupslice.com/hpai/index.php/home/setgcm_id");
		  try {
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		    // Get the deviceID
		    nameValuePairs.add(new BasicNameValuePair("regID", regID));

		    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    HttpResponse response = client.execute(post);
		    BufferedReader rd = 
		      new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		    String line = "";
		    while ((line = rd.readLine()) != null) {
		      line += line+"\n";
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
    }
	
	
	@SuppressWarnings("deprecation")
	private void send_notif(String json,Context context)
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
		notifManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
		if(length > 1)
		{
			myNotification = new Notification(R.drawable.ic_launcher,"Notifikasi HPAI Mobile",System.currentTimeMillis());
		}
		else
		{
			myNotification = new Notification(R.drawable.ic_launcher,titles[length - 1],System.currentTimeMillis());
		}
		
		
		
		//String title = obj2.getString("t");
		//String desc = obj2.getString("d");
		Intent myIntent = new Intent(context,BonusReceiv.class);
		myIntent.putExtra("title", titles);
		myIntent.putExtra("desc", descs);
		myIntent.putExtra("id", ids);
		myIntent.putExtra("image", images);
		myIntent.putExtra("token", token);
		
		PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
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

}
