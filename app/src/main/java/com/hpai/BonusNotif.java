package com.hpai;

import com.hpai.connection.URLConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class BonusNotif extends Activity {

	WebView webView;
	String[] title;
	String[] desc;
	String[] image;
	String[] id2;
	String ids;
	String tokens;
	
	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bonus_notif);

		try{
			webView = (WebView) findViewById(R.id.notif_web);
			webView.getSettings().setJavaScriptEnabled(true);
			/*webView.getSettings().setPluginsEnabled(true);*/
			
			final String token = getIntent().getExtras().getString("token");
			final String id = getIntent().getExtras().getString("id");
			title = getIntent().getExtras().getStringArray("title");
			desc = getIntent().getExtras().getStringArray("desc");
			image = getIntent().getExtras().getStringArray("image");
			id2 = getIntent().getExtras().getStringArray("ids");
			ids = id;
			tokens = token;
			
			CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(this);
	    	CookieManager cookieManager = CookieManager.getInstance();
	    	cookieManager.setAcceptCookie(true);
	    	//cookieManager.removeSessionCookie();
	    	cookieManager.setCookie("http://avo-m.hpai.co.id/index.php/notify","id="+id+"");
	    	cookieManager.setCookie("http://avo-m.hpai.co.id/index.php/notify","token="+token+"");
	    	cookieSyncManager.sync();
	    	
	    	Button btnBack = (Button) findViewById(R.id.notif_web_btn_back);
	    	btnBack.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
					Intent myIntent = new Intent(BonusNotif.this,BonusReceiv.class);
					myIntent.putExtra("title", title);
					myIntent.putExtra("desc", desc);
					myIntent.putExtra("id", id2);
					myIntent.putExtra("image", image);
					myIntent.putExtra("token", token);
					startActivity(myIntent);
				}
			});
	    	
	    	webView.setWebViewClient(new WebViewClient(){
	    		
	    		ProgressDialog dialog = ProgressDialog.show(BonusNotif.this, "", "Please wait ...");
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
	    			//super.onReceivedError(view, errorCode, description, failingUrl);
	    		}
	    	});
	    	
	    	webView.loadUrl(URLConnection.URLNotifyClient);
		}
		catch(Exception e)
		{
			AlertDialog.Builder b = new AlertDialog.Builder(BonusNotif.this);
			b.setMessage(e.getMessage().toString());
			b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			b.create().show();
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
		{
			finish();
			Intent myIntent = new Intent(BonusNotif.this,BonusReceiv.class);
			myIntent.putExtra("title", title);
			myIntent.putExtra("desc", desc);
			myIntent.putExtra("id", id2);
			myIntent.putExtra("image", image);
			myIntent.putExtra("token", tokens);
			startActivity(myIntent);
		}
		return super.onKeyDown(keyCode, event);
	}
}
