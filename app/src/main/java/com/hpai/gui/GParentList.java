package com.hpai.gui;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.hpai.R;
import com.hpai.connection.URLConnection;
import com.hpai.dbhelper.DBConnection;
import com.hpai.lib.internal.AdapterGParent;
import com.hpai.models.MGuessCProduct;
import com.hpai.models.MGuessCTestimoni;
import com.hpai.models.MGuessInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;


public class GParentList extends Activity {

	private ProgressDialog progress;
	ListView listData;
	AdapterGParent adapter;
	String[] path_image;
	String[] title_data;
	String[] jsonID;
	String module;
	
	String[] pdf_url;
	String login = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gparent_list);
        
        String url = getIntent().getExtras().getString("url");
        final String module = getIntent().getExtras().getString("module");
        String title = getIntent().getExtras().getString("title");
        TextView textTitle = (TextView) findViewById(R.id.gparent_header_title);
        textTitle.setText(title);
        this.module = module;
        login = getIntent().getExtras().getString("login");
        Button btnBack = (Button) findViewById(R.id.gparent_btn_back);
        btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(GParentList.this,Home.class);
				i.putExtra("module", module);
				i.putExtra("login", login);
				startActivity(i);
				GParentList.this.finish();
			}
		});
        
        boolean inet = URLConnection.checkConnection(this);
        if(module.equalsIgnoreCase("product")){
        	processProduct(url,inet);
        	refresh(URLConnection.URLCategoryProduk);
        }
        else if(module.equalsIgnoreCase("info"))
        {
        	processInfo(url,inet);
        	refresh(URLConnection.URLInfo);
        }
        else
        {
        	processTestimoni(url,inet);
        	refresh(URLConnection.URLCategoryTestimoni);
        }
    }

    private void processProduct(String url,final boolean inet)
    {
    	
    	new AsyncTask<String, Integer, String>() {

    		protected void onPreExecute() {
    			progress = new ProgressDialog(GParentList.this);
				progress.setMessage("Loading...");
				progress.setCancelable(true);
				progress.setIndeterminate(true);
				progress.show();
				super.onPreExecute();
    		};
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				DBConnection connection = new DBConnection(GParentList.this);
				List<MGuessCProduct> cProduk = connection.get_product_category();
				path_image = new String[cProduk.size()];
				title_data = new String[cProduk.size()];
				jsonID = new String[cProduk.size()];
				int i = 0;
				for(MGuessCProduct cProduct : cProduk){
					jsonID[i] = cProduct.getId();
					path_image[i] = cProduct.getImgUrl();
					title_data[i] = cProduct.getTitle();
					i++;
				}
				connection.close();
				return "";
				//return response;
			}
			
			protected void onPostExecute(String result) {
				progress.dismiss();
				adapter = new AdapterGParent(GParentList.this, path_image, title_data, GParentList.this);
				listData = (ListView) findViewById(R.id.gparent_list);
				listData.setAdapter(adapter);
				listData.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						finish();
						Intent i = new Intent(GParentList.this,GChildList.class);
						i.putExtra("module", module);
						i.putExtra("url", URLConnection.URLProduk+"?c="+jsonID[arg2]);
						i.putExtra("title", "Produk");
						i.putExtra("catid", jsonID[arg2]);
						startActivity(i);
					}
					
				});
				
			};
		}.execute(url);
    	
    }
    
    private void processTestimoni(String url,final boolean inet)
    {
    	
    	new AsyncTask<String, Integer, String>() {

    		protected void onPreExecute() {
    			progress = new ProgressDialog(GParentList.this);
				progress.setMessage("Loading...");
				progress.setCancelable(true);
				progress.setIndeterminate(true);
				progress.show();
				super.onPreExecute();
    		};
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				DBConnection connection = new DBConnection(GParentList.this);
				List<MGuessCTestimoni> cTestimoni = connection.get_testimoni_category();
				path_image = new String[cTestimoni.size()];
				title_data = new String[cTestimoni.size()];
				jsonID = new String[cTestimoni.size()];
				int i = 0;
				for(MGuessCTestimoni cTesti : cTestimoni){
					jsonID[i] = Integer.toString(cTesti.getId());
					path_image[i] = cTesti.getImageUrl();
					title_data[i] = cTesti.getTitle();
					i++;
				}
				connection.close();
				
				return "";
			}
			
			protected void onPostExecute(String result) {
				progress.dismiss();
				adapter = new AdapterGParent(GParentList.this, path_image, title_data, GParentList.this);
				listData = (ListView) findViewById(R.id.gparent_list);
				listData.setAdapter(adapter);
				listData.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						/*Intent i = new Intent(GParentList.this,GDetailList.class);
						i.putExtra("json", jsonID[arg2]);
						i.putExtra("module", module);
						startActivity(i);*/
						finish();
						Intent i = new Intent(GParentList.this,GChildList.class);
						i.putExtra("module", module);
						i.putExtra("url", URLConnection.URLTestimoni+"?c="+jsonID[arg2]);
						i.putExtra("title", "Testimoni");
						i.putExtra("catid", jsonID[arg2]);
						startActivity(i);
					}
					
				});
				
			};
		}.execute(url);
    	
    }
    
    private void processInfo(String url,final boolean inet)
    {
    	
    	new AsyncTask<String, Integer, String>() {

    		protected void onPreExecute() {
    			progress = new ProgressDialog(GParentList.this);
				progress.setMessage("Loading...");
				progress.setCancelable(true);
				progress.setIndeterminate(true);
				progress.show();
				super.onPreExecute();
    		};
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				DBConnection connection = new DBConnection(GParentList.this);
				List<MGuessInfo> infos = connection.get_info();
				path_image = new String[infos.size()];
				title_data = new String[infos.size()];
				jsonID = new String[infos.size()];
				pdf_url = new String[infos.size()];
				int i = 0;
				for(MGuessInfo info : infos)
				{
					
					jsonID[i] = Integer.toString(info.getId());
					pdf_url[i] = info.getPDFUrl();
					path_image[i] = info.getImageUrl();
					title_data[i] = info.getTitle();
					i++;
				}
				return "";
			}
			
			protected void onPostExecute(String result) {
				progress.dismiss();
				adapter = new AdapterGParent(GParentList.this, path_image, title_data, GParentList.this);
				listData = (ListView) findViewById(R.id.gparent_list);
				listData.setAdapter(adapter);
				listData.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						if(!URLConnection.checkConnection(GParentList.this))
						{
							AlertDialog.Builder b = new AlertDialog.Builder(GParentList.this);
							b.setMessage("No internet Connection");
							b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									// TODO Auto-generated method stub
									
								}
							});
							AlertDialog d = b.create();
							d.show();
						}
						else
						{
							finish();
							Intent i = new Intent(GParentList.this,PDFViewer.class);
							i.putExtra("pdf_url", pdf_url[arg2]);
							startActivity(i);
						}
						
					}
					
				});
				
			};
		}.execute(url);
    	
    }
    
    
    
    private void refresh(final String url)
    {
    	ImageButton btnRefresh = (ImageButton) findViewById(R.id.gparent_refresh_button);
    	btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!URLConnection.checkConnection(GParentList.this))
				{
					AlertDialog.Builder b = new AlertDialog.Builder(GParentList.this);
					b.setMessage("No internet Connection");
					b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					});
					AlertDialog d = b.create();
					d.show();
				}
				else
				{
					new AsyncTask<String, Integer, String>(){

						protected void onPreExecute() {
							progress = new ProgressDialog(GParentList.this);
							progress.setMessage("Loading...");
							progress.setCancelable(true);
							progress.setIndeterminate(true);
							progress.show();
						};
						@Override
						protected String doInBackground(String... params) {
							// TODO Auto-generated method stub
							try {
								String response = URLConnection.getJsonString(url);
								JSONArray jsonArray = new JSONArray(response);
								DBConnection connection = new DBConnection(GParentList.this);
								//connection.reset_product();
								if(module.equals("product"))
								{
									connection.reset_product_category();
								}
								else if(module.equals("testimoni"))
								{
									connection.reset_testimoni_category();
								}
								else
								{
									connection.reset_info();
								}
								
								
								for(int i = 0;i < jsonArray.length();i++)
								{
									JSONObject jsonObject = jsonArray.getJSONObject(i);
									if(module.equals("product"))
									{
										MGuessCProduct cProduct = new MGuessCProduct(jsonObject.getString("c"), jsonObject.getString("d"), 
												jsonObject.getString("img"));
										connection.insert_product_category(cProduct);
										download_file(jsonObject.getString("img"));
									}
									else if(module.equals("testimoni"))
									{
										MGuessCTestimoni testi = new MGuessCTestimoni(Integer.parseInt(jsonObject.getString("c")), jsonObject.getString("d"), 
												jsonObject.getString("d"), jsonObject.getString("img"));
										connection.insert_testimoni_category(testi);
										download_file(jsonObject.getString("img"));
									}
									else
									{
										connection.insert_info(new MGuessInfo(Integer.parseInt(jsonObject.getString("i")), 
												jsonObject.getString("t"), jsonObject.getString("d"), jsonObject.getString("th"), 
												jsonObject.getString("url")));
										download_file(jsonObject.getString("th"));
									}
									
									
								}
								connection.close();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return null;
						}
						
						@Override
						protected void onPostExecute(String result) {
							// TODO Auto-generated method stub
							progress.dismiss();
							finish();
							Intent i = new Intent(GParentList.this,GParentList.class);
							i.putExtra("module", module);
							if(module.equals("product"))
							{
								i.putExtra("url", URLConnection.URLCategoryProduk);
								i.putExtra("title", "Produk");
							}
							else if(module.equals("testimoni"))
							{
								i.putExtra("url", URLConnection.URLCategoryTestimoni);
								i.putExtra("title", "Testimoni");
							}
							else
							{
								i.putExtra("url", URLConnection.URLInfo);
								i.putExtra("title", "Info HPAI");
							}
							startActivity(i);
							super.onPostExecute(result);
						}
					}.execute();
				}
			}
		});
    }
    
    private void download_file(String url)
    {
    	if(checkFile(url) == false)
    	{
    		URL u;
			try {
				u = new URL(url);
				HttpURLConnection c = (HttpURLConnection) u.openConnection();
		        c.setRequestMethod("GET");
		        c.setDoOutput(true);
		        //c.setConnectTimeout(1000000);
		        c.connect();
		        //int lengthOfFile = c.getContentLength();
		        String[] split = url.split("/");
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
		        //long total = 0;
		        while ( (len1 = in.read(buffer)) > 0 ) {
		        	//total += len1;
		        	//pDialog.setProgress((int) ((total * 100) / lengthOfFile));
		        	//publishProgress((int) ((total * 100) / lengthOfFile));
		        	f.write(buffer,0,len1);
		        }
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
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode == KeyEvent.KEYCODE_BACK)
    	{
    		finish();
    		Intent i = new Intent(GParentList.this,Home.class);
    		i.putExtra("module", module);
    		i.putExtra("login", login);
			startActivity(i);
			GParentList.this.finish();
    	}
    	return super.onKeyDown(keyCode, event);
    }
}
