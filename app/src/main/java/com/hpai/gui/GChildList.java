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
import com.hpai.lib.internal.AdapterGChild;
import com.hpai.models.MGuessProduct;
import com.hpai.models.MGuessTestimoni;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.Config;

public class GChildList extends Activity {

	DisplayImageOptions opt;
	ImageLoader loader;
	ListView listData;
	AdapterGChild adapter;
	private ProgressDialog progress;
	String[] path_image;
	String[] title_data;
	String[] desc;
	String[] jsonArrays;
	String module;
	String cat;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gchild_list);
        
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
		ImageLoader.getInstance().init(config);
		
		opt = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.product_icon)
				.cacheInMemory(false)
				.cacheOnDisc(true)
				.considerExifParams(true)
				.bitmapConfig(Config.RGB_565)
				.build();

		loader = ImageLoader.getInstance();
        
        
        final String module = getIntent().getExtras().getString("module");
        String title = getIntent().getExtras().getString("title");
        String url = getIntent().getExtras().getString("url");
        this.module = module;
        //cat = getIntent().getExtras().getString("catid");
        
        TextView textTitle = (TextView) findViewById(R.id.gchild_header_title);
        textTitle.setText(title);
        
        Button btnBack = (Button) findViewById(R.id.gchild_btn_back);
        btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(GChildList.this,GParentList.class);
				intent.putExtra("module", module);
				if(module.equalsIgnoreCase("product")){
					String catid = getIntent().getExtras().getString("catid");
		        	intent.putExtra("url", URLConnection.URLCategoryProduk);
		        	intent.putExtra("title", "Kategori");
		        	intent.putExtra("catid", catid);
		        	
		        }
		        else
		        {
		        	String catids = getIntent().getExtras().getString("catid");
		        	intent.putExtra("url", URLConnection.URLCategoryTestimoni);
		        	intent.putExtra("title", "Kategori");
		        	intent.putExtra("catid", catids);
		        	
		        }
				startActivity(intent);
				GChildList.this.finish();
			}
		});
        boolean inet = URLConnection.checkConnection(this);
        if(module.equalsIgnoreCase("product")){
        	String catids = getIntent().getExtras().getString("catid");
        	productDetail(url,inet,catids);
        	refresh(catids, URLConnection.URLProduk+"?c="+catids,"produk");
        }
        else
        {
        	try{
        		String catids = getIntent().getExtras().getString("catid");
            	testimoniDetail(url,inet,catids);
            	refresh(catids, URLConnection.URLTestimoni+"?c="+catids,"testimoni");
        	}catch(Exception ex)
        	{
        		AlertDialog.Builder b = new AlertDialog.Builder(GChildList.this);
        		b.setMessage(ex.getMessage().toString());
        		b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				}).create().show();
        	}
        	
        }
    }

    private void productDetail(String url,final boolean inet,final String catid)
    {
    	new AsyncTask<String, Integer, String>() {

    		protected void onPreExecute() {
    			progress = new ProgressDialog(GChildList.this);
				progress.setMessage("Loading...");
				progress.setCancelable(true);
				progress.setIndeterminate(true);
				progress.show();
				super.onPreExecute();
    		};
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				DBConnection conn = new DBConnection(GChildList.this);
				List<MGuessProduct> produk = conn.get_product_by_category(catid);
				path_image = new String[produk.size()];
				title_data = new String[produk.size()];
				desc = new String[produk.size()];
				jsonArrays = new String[produk.size()];
				int i = 0;
				for(MGuessProduct product : produk)
				{
					jsonArrays[i] = Integer.toString(product.getId());
					path_image[i] = product.getImageUri();
					title_data[i] = product.getTitle();
					desc[i] = Html.fromHtml(product.getDesc().substring(0, 100)).toString();
					i++;
				}
				conn.close();
				return "";
			}
			
			protected void onPostExecute(String result) {
				progress.dismiss();
				adapter = new AdapterGChild(GChildList.this, path_image, title_data,desc, GChildList.this);
				listData = (ListView) findViewById(R.id.gchild_list);
				listData.setAdapter(adapter);
				listData.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						finish();
						Intent i = new Intent(GChildList.this,GDetailList.class);
						i.putExtra("json", jsonArrays[arg2]);
						i.putExtra("module", module);
						i.putExtra("catid", getIntent().getExtras().getString("catid"));
						startActivity(i);
					}
					
				});
			};
		}.execute(url);
    	
    }
    
    private void testimoniDetail(String url,final boolean inet,final String catid)
    {
    	new AsyncTask<String, Integer, String>() {

    		protected void onPreExecute() {
    			progress = new ProgressDialog(GChildList.this);
				progress.setMessage("Loading...");
				progress.setCancelable(true);
				progress.setIndeterminate(true);
				
				progress.show();
				super.onPreExecute();
    		};
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				DBConnection connection = new DBConnection(GChildList.this);
				List<MGuessTestimoni> testiList = connection.get_testimoni_by_category(catid);
				path_image = new String[testiList.size()];
				title_data = new String[testiList.size()];
				desc = new String[testiList.size()];
				jsonArrays = new String[testiList.size()];
				
				int i = 0;
				for(MGuessTestimoni testi : testiList)
				{
					jsonArrays[i] = Integer.toString(testi.getId());
					path_image[i] = testi.getImageUrl();
					title_data[i] = testi.getTitle();
					if(testi.getDesc().length() >= 100)
					{
						desc[i] = Html.fromHtml(testi.getDesc().substring(0,100)).toString();
					}
					else
					{
						desc[i] = Html.fromHtml(testi.getDesc()).toString();
					}
					i++;
				}
				connection.close();
				return "";
			}
			
			protected void onPostExecute(String result) {
				progress.dismiss();
				adapter = new AdapterGChild(GChildList.this, path_image, title_data,desc, GChildList.this);
				listData = (ListView) findViewById(R.id.gchild_list);
				listData.setAdapter(adapter);
				listData.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						finish();
						Intent i = new Intent(GChildList.this,GDetailList.class);
						i.putExtra("json", jsonArrays[arg2]);
						i.putExtra("module", module);
						i.putExtra("catid", getIntent().getExtras().getString("catid"));
						startActivity(i);
					}
					
				});
			};
		}.execute(url);
    	
    }
    
    private void refresh(final String cat,final String url,final String flag)
    {
    	ImageButton btnRefresh = (ImageButton) findViewById(R.id.gchild_refresh_button);
    	btnRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!URLConnection.checkConnection(GChildList.this))
				{
					AlertDialog.Builder b = new AlertDialog.Builder(GChildList.this);
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
							progress = new ProgressDialog(GChildList.this);
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
								DBConnection connection = new DBConnection(GChildList.this);
								//connection.reset_product();
								if(flag.equals("produk"))
								{
									connection.delete_product_by_category(cat);
								}
								else
								{
									connection.delete_testimoni_by_category(cat);
								}
								
								
								for(int i = 0;i < jsonArray.length();i++)
								{
									JSONObject jsonObject = jsonArray.getJSONObject(i);
									if(flag.equals("produk"))
									{
										
										if(connection.check_product_id(Integer.parseInt(jsonObject.getString("i"))) == false)
										{
											connection.insert_product(new MGuessProduct(Integer.parseInt(jsonObject.getString("i")), 
													jsonObject.getString("pn"), jsonObject.getString("d"), jsonObject.getString("url"), 
													jsonObject.getString("c")));
											download_file(jsonObject.getString("url"));
										}
										/*else
										{
											connection.update_product(new MGuessProduct(Integer.parseInt(jsonObject.getString("i")), 
													jsonObject.getString("pn"), jsonObject.getString("d"), jsonObject.getString("url"), 
													jsonObject.getString("c")));
											download_file(jsonObject.getString("url"));
										}*/
									}
									else
									{
										if(connection.check_testimoni_id(Integer.parseInt(jsonObject.getString("i"))) == false)
										{
											connection.insert_testimoni(new MGuessTestimoni(Integer.parseInt(jsonObject.getString("i")), 
													jsonObject.getString("cn"), jsonObject.getString("cn"), jsonObject.getString("t"), 
													jsonObject.getString("url"), jsonObject.getString("c")));
											download_file(jsonObject.getString("url"));
										}
										/*else
										{
											connection.update_testimoni(new MGuessTestimoni(Integer.parseInt(jsonObject.getString("i")), 
													jsonObject.getString("cn"), jsonObject.getString("cn"), jsonObject.getString("t"), 
													jsonObject.getString("url"), jsonObject.getString("c")));
											download_file(jsonObject.getString("url"));
										}*/
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
							Intent i = new Intent(GChildList.this,GChildList.class);
							i.putExtra("module", module);
							if(flag.equals("produk"))
							{
								i.putExtra("url", URLConnection.URLProduk+"?c="+cat);
								i.putExtra("title", "Produk");
							}
							else
							{
								i.putExtra("url", URLConnection.URLTestimoni+"?c="+cat);
								i.putExtra("title", "Testimoni");
							}
							i.putExtra("catid", cat);
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
    		Intent intent = new Intent(GChildList.this,GParentList.class);
			intent.putExtra("module", module);
			if(module.equalsIgnoreCase("product")){
				String catid = getIntent().getExtras().getString("catid");
	        	intent.putExtra("url", URLConnection.URLCategoryProduk);
	        	intent.putExtra("title", "Kategori");
	        	intent.putExtra("catid", catid);
	        }
	        else
	        {
	        	String catids = getIntent().getExtras().getString("catid");
	        	intent.putExtra("url", URLConnection.URLCategoryTestimoni);
	        	intent.putExtra("title", "Kategori");
	        	intent.putExtra("catid", catids);
	        }
			startActivity(intent);
			GChildList.this.finish();
    	}
    	return super.onKeyDown(keyCode, event);
    }
}
