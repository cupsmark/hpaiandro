package com.hpai.connection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class URLConnection {

	public static String URLCategoryProduk = "http://api.hpai.co.id/product/cplist";
	public static String URLCategoryTestimoni = "http://api.hpai.co.id/product/ctlist";
	public static String URLProduk = "http://api.hpai.co.id/product/plist";
	public static String URLTestimoni = "http://api.hpai.co.id/product/tlist";
	public static String URLProfile = "http://api.hpai.co.id/infohpai";
	public static String URLInfo = "http://api.hpai.co.id/infohpai/news";
	public static String URLLogin = "http://api.hpai.co.id/mlogin/login";
	//public static String URLLogin = "http://cupslice.com/hpai/index.php/home/fetch";
	public static String URLHome = "http://avo-m.hpai.co.id/index.php/home";
	public static String URLNotifyClient = "http://avo-m.hpai.co.id/index.php/notify";
	public static String URLNotify = "http://api.hpai.co.id/notify";
	public static String URLConfig = "http://api.hpai.co.id/init/cfg";
	//public static String URLConfig = "http://cupslice.com/hpai/index.php/home/cfg";
	//public static String URLNotify = "http://cupslice.com/hpai/index.php/home/notify";
	public static String URLThread = "http://cupslice.com/hpai/index.php/thread/get_message";
	public static String URLThreadSave = "http://cupslice.com/hpai/index.php/thread/set_message";
	public static String URLThreadDelete = "http://cupslice.com/hpai/index.php/thread/delete_message";
	//public static String URLThreadImage = "http://cupslice.com/hpai/uploads/default-user.png";
	public static String URLThreadImage = "http://api.hpai.co.id/mlogin/avatar";
	public static String FB_APP_ID = "530648040380300";
	
	public static boolean checkConnection(Context context)
	{		
		
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) 
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) 
                for (int i = 0; i < info.length; i++) 
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
	}
	
	public static String getJsonString(String url)
	{
		InputStream is = null;
		String result = "";
		int timeoutConnection = 180000;
		// HTTP
		try {	    	
			HttpParams param = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(param, timeoutConnection);
			
			HttpClient httpclient = new DefaultHttpClient(param); // for port 80 requests!
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch(Exception e) {
			return null;
		}
	    
		// Read response to string
		try {	    	
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();	            
		} catch(Exception e) {
			return null;
		}
 
    
		return result;
	}
	
	public static String postJsonLogin(String url,String username,String password)
	{
		InputStream is = null;
		String result = "";
		
		// HTTP
		try {	    	
			HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
			HttpPost httpPost = new HttpPost(url);
			
			List<NameValuePair> paramPost = new ArrayList<NameValuePair>();
			paramPost.add(new BasicNameValuePair("username", username));
			paramPost.add(new BasicNameValuePair("password", password));
			
			httpPost.setEntity(new UrlEncodedFormEntity(paramPost));
			HttpResponse response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
		} catch(Exception e) {
			return "0";
		}
	    
		// Read response to string
		try {	    	
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();	            
		} catch(Exception e) {
			return "0";
		}
 
    
		return result;
	}
	
	
}
