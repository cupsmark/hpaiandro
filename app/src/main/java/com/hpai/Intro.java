package com.hpai;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hpai.connection.URLConnection;
import com.hpai.dbhelper.DBConnection;
import com.hpai.gui.Home;
import com.hpai.lib.internal.BonusNotificationService;
import com.hpai.lib.internal.GCMUtilities;
import com.hpai.models.MGuessCProduct;
import com.hpai.models.MGuessCTestimoni;
import com.hpai.models.MGuessInfo;
import com.hpai.models.MGuessProduct;
import com.hpai.models.MGuessProfile;
import com.hpai.models.MGuessTestimoni;
import com.hpai.models.MUsers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;


public class Intro extends Activity {

	private int count_data = 0;
	private ArrayList<String> image_url;
	private int counter = 0;
	String interval = "1200000";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        
       
		
        new AsyncTask<Void, Void, Void>()
        {

        	@Override
        	protected void onPreExecute() {
        		// TODO Auto-generated method stub
        		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        		super.onPreExecute();
        	}
			@Override
			protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
				DBConnection conn = new DBConnection(Intro.this);
				conn.close();
                
				return null;
			}
        	
			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				//execute_user();
				DBConnection con = new DBConnection(Intro.this);
				if((!con.check_product_category_empty()) && (!con.check_product_empty()) && (!con.check_testimoni_category_empty()) && (!con.check_testimony_empty()) && (!con.check_profile_empty()) && (!con.check_info_empty()))
				{
					if(!URLConnection.checkConnection(Intro.this))
					{
						AlertDialog.Builder b = new AlertDialog.Builder(Intro.this);
						b.setMessage("No internet connection");
						b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								System.exit(0);
							}
						});
						AlertDialog d = b.create();
						d.show();
					}
					else
					{
						setup_data();
					}
					
				}
				else
				{
					Intent intent = new Intent(Intro.this,Home.class);
					intent.putExtra("module", "");
					intent.putExtra("login", "falset");
			        startActivity(intent);
			        finish();
				}
				con.close();
				
				super.onPostExecute(result);
			}
        }.execute();
    }

    private void setup_data()
    {
    	new AsyncTask<String, Integer, String>(){

    		ProgressDialog pDialog;
    		
    		protected void onPreExecute() {
    			image_url = new ArrayList<String>();
    			pDialog = new ProgressDialog(Intro.this);
    			pDialog.setMessage("Setup Data. Please wait ...");
    			pDialog.setIndeterminate(true);
    			pDialog.setCancelable(true);
    			pDialog.show();
    		};
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				execute_user();
				execute_product_category();
				execute_product();
				execute_testimoni_category();
				execute_testimoni();
				execute_info();
				execute_profile();
				return "";
			}
    		
			protected void onPostExecute(String result) {
				pDialog.dismiss();
				download_file();
			};
    	}.execute();
    }
    
    private void download_file()
    {
    	new AsyncTask<String, Integer, String>(){

    		ProgressDialog pDialog;
    		
    		protected void onPreExecute() {
    			pDialog = new ProgressDialog(Intro.this);
    			pDialog.setTitle("Please wait ...");
    			pDialog.setIndeterminate(false);
    			pDialog.setMax(100);
    			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    			pDialog.setCancelable(true);
    			pDialog.show();
    		};
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				for(int i = 0;i < image_url.size();i++)
				{
					if(checkFile(image_url.get(i)) == false){
						URL u;
						try {
							u = new URL(image_url.get(i));
							HttpURLConnection c = (HttpURLConnection) u.openConnection();
					        c.setRequestMethod("GET");
					        c.setDoOutput(true);
					        //c.setConnectTimeout(1000000);
					        c.connect();
					        int lengthOfFile = c.getContentLength();
					        String[] split = image_url.get(i).split("/");
							String filename = split[split.length - 1];
					        File myDir = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "HPAI" + File.separator);
							if(!myDir.exists())
							{
								myDir.mkdir();
							}
							File files = new File(myDir,filename);
					        FileOutputStream f = new FileOutputStream(files);
					        InputStream in = c.getInputStream();

					        byte[] buffer = new byte[1024];
					        int len1 = 0;
					        long total = 0;
					        while ( (len1 = in.read(buffer)) > 0 ) {
					        	total += len1;
					        	//pDialog.setProgress((int) ((total * 100) / lengthOfFile));
					        	publishProgress((int) ((total * 100) / lengthOfFile));
					        	f.write(buffer,0,len1);
					        }
					        counter += 1;
					        f.close();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				
				return null;
			}
    		
			protected void onProgressUpdate(Integer... values) {
				pDialog.setProgress(values[0]);
				pDialog.setMessage("Download "+(counter) +" / " + image_url.size() );
			};
			
			protected void onPostExecute(String result) {
				pDialog.dismiss();
				//DBConnection dbs = new DBConnection(Intro.this);
				//dbs.delete_user("001");
				
	    		
				Intent intent = new Intent(Intro.this,Home.class);
				intent.putExtra("module", "");
				intent.putExtra("login", "falset");
		        startActivity(intent);
		        finish();
			};
    	}.execute();
    }
    
    
    //LOAD JSON
    private void execute_product_category()
    {
    	try {
			String response = URLConnection.getJsonString(URLConnection.URLCategoryProduk);
			DBConnection conn = new DBConnection(Intro.this);
			JSONArray jsonArray = new JSONArray(response);
			//conn.reset_product_category();
			count_data += jsonArray.length();
			
			for(int i = 0;i < jsonArray.length();i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(!conn.check_cat_product_id(jsonObject.getString("c")))
				{
					MGuessCProduct cProduct = new MGuessCProduct(jsonObject.getString("c"), jsonObject.getString("d"), 
							jsonObject.getString("img"));
					conn.insert_product_category(cProduct);
					image_url.add(jsonObject.getString("img"));
				}
				/*else
				{
					conn.delete_c_product(jsonObject.getString("c"));
					MGuessCProduct cProduct = new MGuessCProduct(jsonObject.getString("c"), jsonObject.getString("d"), 
							jsonObject.getString("img"));
					conn.insert_product_category(cProduct);
					image_url.add(jsonObject.getString("img"));
				}*/
			}
			conn.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void execute_product()
    {
    	try {
			String response = URLConnection.getJsonString(URLConnection.URLProduk);
			JSONArray jsonArray = new JSONArray(response);
			DBConnection connection = new DBConnection(Intro.this);
			count_data += jsonArray.length();
			//connection.reset_product();
			for(int i = 0;i < jsonArray.length();i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(connection.check_product_id(Integer.parseInt(jsonObject.getString("i"))) == false)
				{
					connection.insert_product(new MGuessProduct(Integer.parseInt(jsonObject.getString("i")), 
							jsonObject.getString("pn"), jsonObject.getString("d"), jsonObject.getString("url"), 
							jsonObject.getString("c")));
					image_url.add(jsonObject.getString("url"));
				}
				/*else
				{
					connection.delete_product_by_id(Integer.parseInt(jsonObject.getString("i")));
					connection.insert_product(new MGuessProduct(Integer.parseInt(jsonObject.getString("i")), 
							jsonObject.getString("pn"), jsonObject.getString("d"), jsonObject.getString("url"), 
							jsonObject.getString("c")));
					image_url.add(jsonObject.getString("url"));
				}*/
			}
			
			connection.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void execute_testimoni_category()
    {
    	try {
			String response = URLConnection.getJsonString(URLConnection.URLCategoryTestimoni);
			DBConnection conn = new DBConnection(Intro.this);
			JSONArray jsonArray = new JSONArray(response);
			count_data += jsonArray.length();
			//conn.reset_testimoni_category();
			for(int i = 0;i < jsonArray.length();i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(!conn.check_cat_testimoni_id(Integer.parseInt(jsonObject.getString("c"))))
				{
					MGuessCTestimoni testi = new MGuessCTestimoni(Integer.parseInt(jsonObject.getString("c")), jsonObject.getString("d"), 
							jsonObject.getString("d"), jsonObject.getString("img"));
					conn.insert_testimoni_category(testi);
					image_url.add(jsonObject.getString("img"));
				}
				/*else
				{
					conn.delete_c_testimoni(Integer.parseInt(jsonObject.getString("c")));
					MGuessCTestimoni testi = new MGuessCTestimoni(Integer.parseInt(jsonObject.getString("c")), jsonObject.getString("d"), 
							jsonObject.getString("d"), jsonObject.getString("img"));
					conn.insert_testimoni_category(testi);
					image_url.add(jsonObject.getString("img"));
				}*/
				
				
			}
			conn.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void execute_testimoni()
    {
    	try {
			String response = URLConnection.getJsonString(URLConnection.URLTestimoni);
			DBConnection conn = new DBConnection(Intro.this);
			JSONArray jsonArray = new JSONArray(response);
			count_data += jsonArray.length();
			for(int i = 0;i < jsonArray.length();i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(conn.check_testimoni_id(Integer.parseInt(jsonObject.getString("i"))) == false)
				{
					conn.insert_testimoni(new MGuessTestimoni(Integer.parseInt(jsonObject.getString("i")), 
							jsonObject.getString("cn"), jsonObject.getString("cn"), jsonObject.getString("t"), 
							jsonObject.getString("url"), jsonObject.getString("c")));
					image_url.add(jsonObject.getString("url"));
				}
				/*else
				{
					conn.delete_testimoni_by_id(Integer.parseInt(jsonObject.getString("i")));
					conn.insert_testimoni(new MGuessTestimoni(Integer.parseInt(jsonObject.getString("i")), 
							jsonObject.getString("cn"), jsonObject.getString("cn"), jsonObject.getString("t"), 
							jsonObject.getString("url"), jsonObject.getString("c")));
					image_url.add(jsonObject.getString("url"));
				}*/
			}
			conn.close();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void execute_profile()
    {
    	try {
			String response = URLConnection.getJsonString(URLConnection.URLProfile);
			JSONArray jsonArray = new JSONArray(response);
			DBConnection conn = new DBConnection(Intro.this);
			count_data += jsonArray.length();
			for(int i = 0;i < jsonArray.length();i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(conn.check_profile_id(Integer.parseInt(jsonObject.getString("i"))) == false)
				{
					conn.insert_profile(new MGuessProfile(Integer.parseInt(jsonObject.getString("i")), 
							jsonObject.getString("t"), jsonObject.getString("d"), jsonObject.getString("url")));
					image_url.add(jsonObject.getString("url"));
				}
				
			}
			conn.close();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void execute_info()
    {
    	try {
			String response = URLConnection.getJsonString(URLConnection.URLInfo);
			JSONArray jsonArray = new JSONArray(response);
			DBConnection conn = new DBConnection(Intro.this);
			count_data += jsonArray.length();
			for(int i = 0;i < jsonArray.length();i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				if(conn.check_info_id(Integer.parseInt(jsonObject.getString("i"))) == false)
				{
					conn.insert_info(new MGuessInfo(Integer.parseInt(jsonObject.getString("i")), 
							jsonObject.getString("t"), jsonObject.getString("d"), jsonObject.getString("th"), 
							jsonObject.getString("url")));
					image_url.add(jsonObject.getString("th"));
				}
				/*else
				{
					conn.delete_info_by_id(Integer.parseInt(jsonObject.getString("i")));
					conn.insert_info(new MGuessInfo(Integer.parseInt(jsonObject.getString("i")), 
							jsonObject.getString("t"), jsonObject.getString("d"), jsonObject.getString("th"), 
							jsonObject.getString("url")));
					image_url.add(jsonObject.getString("th"));
				}*/
				
			}
			conn.close();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void execute_user()
    {
    	final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
    	final Date date = new Date();
    	final DBConnection db = new DBConnection(this);
    	//interval = "1200000";
    	
					String response = URLConnection.getJsonString(URLConnection.URLConfig);
					if(response != null)
					{
						JSONObject obj;
						try {
							obj = new JSONObject(response);
							String intval = obj.getString("int");
							int convert = 1000 * 60 * Integer.parseInt(intval);
							interval = Integer.toString(convert);
							db.insert_user(new MUsers("001", "system", "", "", format.format(date),GCMUtilities.deviceId(Intro.this),interval,""));
							
							Intent myIntent = new Intent(Intro.this, BonusNotificationService.class);
							myIntent.setAction("run");
							
							PendingIntent pendingIntent = PendingIntent.getService(Intro.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
							AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
							//alarmManager.cancel(pendingIntent);
							alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), convert, pendingIntent);   
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else
					{
						db.insert_user(new MUsers("001", "system", "", "", format.format(date),GCMUtilities.deviceId(Intro.this),interval,""));
						Intent myIntent = new Intent(Intro.this, BonusNotificationService.class);
						myIntent.setAction("run");
						
						PendingIntent pendingIntent = PendingIntent.getService(Intro.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
						AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
						//alarmManager.cancel(pendingIntent);
						alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1200000, pendingIntent);   
					}
					
    }
    
    private boolean checkFile(String url)
	{
		boolean result = false;
		String[] split = url.split("/");
		File files = new File(Environment.getExternalStorageDirectory().getPath()+"/HPAI/", split[split.length - 1]);
		if(files.exists())
		{
			result = true;
		}
		else
		{
			result = false;
		}
		return result;
	}
}
