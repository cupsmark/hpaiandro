package com.hpai.gui;

import java.util.List;
import com.hpai.GCMBroadcastReceiver;
import com.hpai.R;
import com.hpai.connection.URLConnection;
import com.hpai.dbhelper.DBConnection;
import com.hpai.models.MUsers;

import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MemberActivity extends Activity {

	WebView webView;
	String sess_cookie;
	CookieManager cookie_manager;
	
	String names = "";
	String uids = "";
	String token = "";
	String login = "";
	
	AlarmManager alarmManager;
	PendingIntent pendingIntent;
	
	String regID = "";
	
	AsyncTask<String, Integer, String> mRegisterTask;
	GCMBroadcastReceiver receiver;
	
    @SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        
        token = getToken();
        
        /*try{
        	Intent myIntent = new Intent(MemberActivity.this, BonusNotificationService.class);
    		myIntent.setAction("run");
    		myIntent.putExtra("token", token);
    		pendingIntent = PendingIntent.getService(MemberActivity.this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT|  Intent.FILL_IN_DATA);
    		alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    		alarmManager.cancel(pendingIntent);
    		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 180000, pendingIntent);        }
        catch(Exception ex)
        {
        	AlertDialog.Builder b = new AlertDialog.Builder(MemberActivity.this);
        	b.setMessage(ex.getMessage().toString());
        	b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).create().show();
        }*/
        
        
        
        
        
        
        webView = (WebView) findViewById(R.id.member_web);
        webView.getSettings().setJavaScriptEnabled(true);
        /*webView.getSettings().setPluginsEnabled(true);*/
        final String name = getIntent().getExtras().getString("r");
    	final String id = getIntent().getExtras().getString("u");
    	final String tokens = getIntent().getExtras().getString("t");
    	
    	names = name;
    	uids = id;
    	//token = tokens;
    	login = getIntent().getExtras().getString("login");
    	
    	
    	
    	CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
    	final CookieManager cookieManager = CookieManager.getInstance();
    	cookieManager.setAcceptCookie(true);
    	//cookieManager.removeSessionCookie();
    	cookieManager.setCookie(URLConnection.URLHome,"uid="+id+"");
    	cookieManager.setCookie(URLConnection.URLHome,"nama="+name+"");
    	cookieManager.setCookie(URLConnection.URLHome,"token="+tokens+"");
    	
    	cookieSyncManager.sync();
    	
    	
		
    	TextView title = (TextView) findViewById(R.id.member_header_title);
    	title.setText(name);
    	Button btnLogout = (Button) findViewById(R.id.member_btn_logout);
    	btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AsyncTask<String, Integer, String>(){

					ProgressDialog dialog = ProgressDialog.show(MemberActivity.this, "", "Please wait ...");
					
					protected void onPreExecute() {
						dialog.show();
					};
					
					@Override
					protected String doInBackground(String... params) {
						// TODO Auto-generated method stub
						
						cookieManager.removeAllCookie();
						cookieManager.removeSessionCookie();
						DBConnection dbs = new DBConnection(MemberActivity.this);
						List<MUsers> user = dbs.userIsLogin();
						String id = "";
						for(MUsers us : user)
						{
							id = us.getUID();
						} 
						dbs.update_user_is_login("logout",id);
						dbs.update_user_token("", id);
						return "";
					}
					
					protected void onPostExecute(String result) {
						dialog.dismiss();
						finish();
						Intent i = new Intent(MemberActivity.this,Home.class);
						i.putExtra("module", "");
						i.putExtra("login", "falset");
						startActivity(i);
					};
					
				}.execute();
				
			}
		});
    	Button btnBack = (Button) findViewById(R.id.member_btn_back);
    	btnBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(MemberActivity.this,Home.class);
				i.putExtra("module", "member");
				i.putExtra("r", name);
				i.putExtra("u", id);
				i.putExtra("t", tokens);
				i.putExtra("login", "truet");
				startActivity(i);
			}
		});
    	webView.setWebViewClient(new WebViewClient(){
    		
    		ProgressDialog dialog = ProgressDialog.show(MemberActivity.this, "", "Please wait ...");
    		@Override
    		public void onPageStarted(WebView view, String url, Bitmap favicon) {
    			// TODO Auto-generated method stub
    			dialog.show();
    			    			
    			super.onPageStarted(view, url, favicon);
    		}
    		
    		@Override
    		public void onPageFinished(WebView view, String url) {
    			// TODO Auto-generated method stub
    			dialog.dismiss();
    			//cookieManager.setAcceptCookie(true);

    			super.onPageFinished(view, url);
    		}
    		
    		
    		@Override
    		public void onReceivedError(WebView view, int errorCode,
    				String description, String failingUrl) {
    			// TODO Auto-generated method stub
    			if(errorCode == ERROR_HOST_LOOKUP || errorCode == ERROR_FILE_NOT_FOUND || errorCode == ERROR_CONNECT || 
    					errorCode == ERROR_UNKNOWN || errorCode == ERROR_BAD_URL) 
    			{
    				webView.loadUrl("file:///android_asset/not_found.html");
    			}
    		}
    	});
    	webView.loadUrl(URLConnection.URLHome);
    	
    	
    }
   
       
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode == KeyEvent.KEYCODE_BACK)
    	{
    		finish();
    		Intent i = new Intent(MemberActivity.this,Home.class);
			i.putExtra("module", "member");
			i.putExtra("r", names);
			i.putExtra("u", uids);
			i.putExtra("t", token);
			i.putExtra("login", "truet");
			startActivity(i);
    	}
    	return super.onKeyDown(keyCode, event);
    }

    private String getToken()
	{
		String token = "";
		DBConnection conn = new DBConnection(MemberActivity.this);
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
