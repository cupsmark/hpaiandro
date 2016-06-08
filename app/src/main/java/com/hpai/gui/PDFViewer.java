package com.hpai.gui;


import com.hpai.R;
import com.hpai.connection.URLConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;

public class PDFViewer extends Activity{

	private WebView webView;
	private Button btnBack;
    
	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfviewer);
        
        btnBack = (Button) findViewById(R.id.pdf_btn_back);
        btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent j = new Intent(PDFViewer.this,GParentList.class);
				j.putExtra("title", "Info HPAI");
				j.putExtra("url", URLConnection.URLInfo);
				j.putExtra("module", "info");
				startActivity(j);
			}
		});
        String pdf_url = getIntent().getExtras().getString("pdf_url");
        
        webView = (WebView) findViewById(R.id.pdf_web_container);
        webView.getSettings().setJavaScriptEnabled(true);
        /*webView.getSettings().setPluginsEnabled(true);*/
        pdfLoadImages(pdf_url);
    }

	
    
    private void pdfLoadImages(final String files)
    {
        try
        {
            new AsyncTask<Void, Void, Void>()
                    {
                        ProgressDialog progressDialog = ProgressDialog.show(PDFViewer.this, "", "Converting ...");
                        @Override
                        protected void onPostExecute(Void result)
                        {
                            progressDialog.dismiss();
                        }

                        @Override
                        protected Void doInBackground(Void... params)
                        {
                            try
                            {
                            	webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+files);
                            }
	                        catch (Exception e)
	                        {
	                            e.printStackTrace();
	                        }
                        	
                            return null;
                        	
                        }
                        
                        
                    }.execute();
                    System.gc();// run GC
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
