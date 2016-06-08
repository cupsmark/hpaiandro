package com.hpai;

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


import com.google.android.gcm.GCMBaseIntentService;
import com.hpai.connection.URLConnection;
import com.hpai.dbhelper.DBConnection;
import com.hpai.lib.internal.GCMUtilities;
import com.hpai.models.MUsers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import static com.hpai.lib.internal.GCMUtilities.displayMessage;



public class GCMIntentService extends GCMBaseIntentService{
	private NotificationManager notifManager;
	private Notification myNotification;
	

	String token = "";
	String[] ids;
	String[] titles;
	String[] descs;
	String[] images;
	
	
	
	
	public GCMIntentService()
	{
		super(GCMUtilities.SENDER_ID);
	}
	
	@Override
	protected void onError(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		displayMessage(arg0, "error gcm");
	}

	@Override
	protected void onMessage(Context arg0, final Intent arg1) {
		// TODO Auto-generated method stub
		String message = arg1.getExtras().getString("message");
        //displayMessage(arg0, message);
		String token = getToken(arg0);
		if(!token.equals(""))
		{
			handleMessage(arg0, arg1, message);
		}
        
	}




	@Override
	protected void onRegistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		//displayMessage(arg0, "Message received");
		String uid = get_uid(arg0);
		if((!arg1.equals("")) || (!arg1.equals(null)) || (!arg1.equals("null")) || arg1 != null)
		{
			update_regid(uid, arg1, arg0);
		}
		
		String token = getToken(arg0);
		//String regid = get_regid(arg0);
		if(arg1 != null || (!arg1.equals("null")) || (!arg1.equals(null)) || (!arg1.equals("")))
		{
			sendIDToServer(arg1, token);
		}
		//GCMUtilities.regID = arg1;
		//storeRegistrationId(arg0, arg1);
		//GCMServerUtils.register(arg0, arg1);
	}




	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		// TODO Auto-generated method stub
		displayMessage(arg0, "Unregistered");
		//if (GCMRegistrar.isRegisteredOnServer(arg0)) {
            //GCMServerUtils.unregister(arg0, arg1);
			//String token = getToken(arg0);
			//sendIDToServer(arg1, token);
        //}
	}	
	
	
	public void sendIDToServer(String regID,String token)
    {
		HttpClient client = new DefaultHttpClient();
		  HttpPost post = new HttpPost(GCMUtilities.SERVER_URL+"setgcm_id");
		  try {
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		    // Get the deviceID
		    nameValuePairs.add(new BasicNameValuePair("regID", regID));
		    nameValuePairs.add(new BasicNameValuePair("token", token));

		    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    HttpResponse response = client.execute(post);
		    BufferedReader rd = 
		      new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		    String line = "";
		    while ((line = rd.readLine()) != null) {
		      line +=line+"\n";
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
		  }
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
	
	private String get_uid(Context context)
	{
		String token = "";
		DBConnection conn = new DBConnection(context);
		List<MUsers> u = conn.userIsLogin();
		if(u.size() > 0)
		{
			for(MUsers us : u)
			{
				token = us.getUID();
			} 	
		}
		else
		{
			token = "";
		}
		return token;
	}
	
	
	
	
	
	private void update_regid(String uid,String regid,Context context)
	{
		DBConnection dbs = new DBConnection(context);
		dbs.update_user_reg_id(regid, uid);
	}
	
	
	private void handleMessage(final Context context,final Intent intent,final String message)
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
						//String message = intent.getStringExtra("message");
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
