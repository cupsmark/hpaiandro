package com.hpai.gui;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
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

import com.hpai.R;
import com.hpai.connection.URLConnection;
import com.hpai.dbhelper.DBConnection;
import com.hpai.lib.internal.PullToRefreshListView;
import com.hpai.lib.internal.PullToRefreshListView.OnRefreshListener;
import com.hpai.lib.internal.ThreadAdapter;
import com.hpai.models.MStatus;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThreadRoom extends Activity {

	PullToRefreshListView listView;
	ThreadAdapter adapter;
	int count_data = 0;
	String uid;
	String name;
	String token;
	String urlimages;
	
	private ArrayList<String> imgs;
	private ArrayList<String> ids;
	private ArrayList<String> uids;
	private ArrayList<String> text;
	private ArrayList<String> time;
	private ArrayList<String> uname;
	private ArrayList<String> urlimgs;
	String urls = "0";
	//private ArrayList<String> flag;
	
	DisplayImageOptions opt;
	ImageLoader loader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_room);
		
		btnBack();
		uid = getIntent().getExtras().getString("u");
		name = getIntent().getExtras().getString("r");
		token = getIntent().getExtras().getString("t");
		urlimages = getIntent().getExtras().getString("urlimages");
		
		
		
		 ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ThreadRoom.this)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.denyCacheImageMultipleSizesInMemory()
			.discCacheFileNameGenerator(new Md5FileNameGenerator())
			.tasksProcessingOrder(QueueProcessingType.LIFO)
			.writeDebugLogs() // Remove for release app
			.build();
			ImageLoader.getInstance().init(config);
			
			opt = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.product_icon)
			.cacheInMemory(false)
			.cacheOnDisc(true)
			.considerExifParams(true)
			.bitmapConfig(Config.RGB_565)
			.build();
			
			loader = ImageLoader.getInstance();
		
		load();
		add();
	}
	
	
	
	private void load()
	{
		DBConnection conn = new DBConnection(ThreadRoom.this);
		int off = conn.get_count_status();
		if(off <= 0)
		{
			count_data = 0;
		}
		else
		{
			count_data = off;
		}
		load_data("first");
		
		
		
	}
	
	

	private String fill_listview()
	{
		imgs = new ArrayList<String>();
		ids = new ArrayList<String>();
		uids = new ArrayList<String>();
		text = new ArrayList<String>();
		time = new ArrayList<String>();
		uname = new ArrayList<String>();
		urlimgs = new ArrayList<String>();
		adapter = new ThreadAdapter(ThreadRoom.this);
		String result = "0";
		DBConnection conn = new DBConnection(ThreadRoom.this);
		if(count_data != 0)
		{
			List<MStatus> status= conn.get_status();
			for(MStatus s : status)
			{
				imgs.add(s.getStatusPict());
				ids.add(s.getStatusID());
				text.add(s.getStatusText());
				time.add(s.getStatusTime());
				uids.add(s.getUID());
				uname.add(s.getStatusUname());
				urlimgs.add(s.getStatusPict());
				adapter.add(s.getStatusPict(), s.getStatusUname(), s.getStatusText(),s.getStatusTime());
			}
			//adapter = new ThreadAdapter(imgs, uids, text, ThreadRoom.this);
			adapter.notifyDataSetChanged();
			result = "1";
		}
		else
		{
			result = "0";
		}
		conn.close();
		return result;
	}
	
	private void add()
	{
		Button btnAdd = (Button) findViewById(R.id.thread_btn_logout);
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final Dialog dialogs = new Dialog(ThreadRoom.this);
		    	dialogs.setContentView(R.layout.thread_add);
		    	dialogs.setCancelable(true);
		    	final EditText txtStatusText = (EditText) dialogs.findViewById(R.id.thread_add_text);
		    	Button btnSave = (Button) dialogs.findViewById(R.id.thread_add_save);
		    	btnSave.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(!URLConnection.checkConnection(ThreadRoom.this))
						{
							dialogMessage("No internet connection");
						}
						else
						{
							SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
							Date date = new Date();
							SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyyMMddHHmm",Locale.getDefault());
							
							
							final String status_id = dateFormat2.format(date);
							final String status_text = txtStatusText.getText().toString();
							final String status_time = dateFormat.format(date);
							final String status_flag = "r";
							
							
							new AsyncTask<String, Integer, String>(){
								ProgressDialog dialog = ProgressDialog.show(ThreadRoom.this, "", "Please wait ...");
								protected void onPreExecute() {
									dialog.show();
								};
								
								@Override
								protected String doInBackground(String... params) {
									// TODO Auto-generated method stub
									String result = "0";
									DBConnection conn = new DBConnection(ThreadRoom.this);
									conn.insert_status(new MStatus(status_id, uid, status_text, status_time, status_flag,name,urlimages));
									String js = postStatus(URLConnection.URLThreadSave, status_id, uid, status_text, status_time, status_time,name,urlimages);
									if(js != null)
									{
										result = js;
									}
									else
									{
										result = "0";
									}
									return result;
								}
								
								protected void onPostExecute(String result) {
									dialog.dismiss();
									//displayMessage(result);
									if(!result.equals("0"))
									{
										dialogs.dismiss();
										load_data("first");
									}
								};
								
							}.execute();
						}
						
					}
				});
		    	dialogs.show();
			}
		});
	}
	
	
	
	
	public String postStatus(String url,String status_id,String uid,String text,String time,String flag,String name,String urlimages)
	{
		InputStream is = null;
		String result = "";
		
		// HTTP
		try {	    	
			HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
			HttpPost httpPost = new HttpPost(url);
			
			List<NameValuePair> paramPost = new ArrayList<NameValuePair>();
			paramPost.add(new BasicNameValuePair("status_id", status_id));
			paramPost.add(new BasicNameValuePair("uid", uid));
			paramPost.add(new BasicNameValuePair("status_text", text));
			paramPost.add(new BasicNameValuePair("status_time", time));
			paramPost.add(new BasicNameValuePair("uname", name));
			paramPost.add(new BasicNameValuePair("status_img", urlimages));
			
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
	
	private void load_data(final String flags)
	{
		new AsyncTask<String, Integer, String>(){

			ProgressDialog dialog;
			protected void onPreExecute() {
				if(flags.equals("first"))
				{
					dialog = ProgressDialog.show(ThreadRoom.this, "", "Please wait ...");
					dialog.show();
				}
				
			};
			
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				String result = "0";
				if(flags.equals("first")){
					result = fill_listview();
					
					if(!result.equals("0"))
					{
						result = "1";
					}
					else
					{
						result = "0";
					}
				}
				else
				{
					
					String js = URLConnection.getJsonString(URLConnection.URLThread+"?uid="+uid+"&token="+token+"&off="+count_data);
					result = js;
					if(js != null)
					{
						try {
							JSONObject obj = new JSONObject(js);
							if(!obj.getString("results").equals("0"))
							{
								DBConnection cons = new DBConnection(ThreadRoom.this);
								JSONArray arr = new JSONArray(obj.getString("data"));
								for(int i = 0;i < arr.length();i++)
								{
									JSONObject obj2 = arr.getJSONObject(i);
									if(!cons.check_status_id(obj2.getString("status_id")))
									{
										cons.insert_status(new MStatus(obj2.getString("status_id"), obj2.getString("uid"), 
												obj2.getString("status_text"), obj2.getString("status_time"), "r",obj2.getString("uname"),obj2.getString("status_img")));
										//adapter.add("", obj2.getString("uid"), obj2.getString("status_text"));
									}
								}
								//adapter.notifyDataSetChanged();
								fill_listview();
								
								result = "1";
								
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
				}
				
				return result;
			}
			
			protected void onPostExecute(String result) {
				if(!result.equals("0"))
				{
					if(flags.equals("first"))
					{
						dialog.dismiss();
					}
					listView = (PullToRefreshListView) findViewById(R.id.thread_listview);
					listView.setAdapter(adapter);
					
					listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							finish();
							Intent intent = new Intent(ThreadRoom.this,ThreadDetail.class);
							intent.putExtra("ids", ids.get(position - 1));
							intent.putExtra("imgs", imgs.get(position - 1));
							intent.putExtra("text", text.get(position - 1));
							intent.putExtra("time", time.get(position - 1));
							intent.putExtra("uids", uids.get(position - 1));
							intent.putExtra("uname", uname.get(position - 1));
							intent.putExtra("urlimgs", urlimgs.get(position - 1));
							intent.putExtra("module", "thread");
							intent.putExtra("login", "truet");
							intent.putExtra("uid", uid);
							intent.putExtra("name", name);
							intent.putExtra("token", token);
							intent.putExtra("urlimages", urlimages);
							startActivity(intent);
						}
					});
					listView.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							//Toast.makeText(ThreadRoom.this, "UID:"+ids.get(position - 1)+"-TEXT:"+text.get(position - 1), Toast.LENGTH_LONG).show();
							if(!uid.equals(ids.get(position - 1)))
							{
								Toast.makeText(ThreadRoom.this, "Anda tidak bisa menghapus thread ini", Toast.LENGTH_SHORT).show();
							}
							else
							{
								deleteDialog(ids.get(position - 1), uids.get(position - 1));
							}
							return false;
						}
					});
					listView.setOnRefreshListener(new OnRefreshListener() {
							
							@Override
							public void onRefresh() {
								// TODO Auto-generated method stub
								if(!URLConnection.checkConnection(ThreadRoom.this))
								{
									dialogMessage("No internet connection");
								}
								else
								{
									load_data("refresh");
									
									//adapter.notifyDataSetChanged();
									listView.postDelayed(new Runnable() {
										@Override
										public void run() {
											listView.onRefreshComplete();
										}
									}, 2000);
								}
								
							}
					});
					
					
					
					
				}
			};
		}.execute();
	}
	
	private void deleteDialog(final String id,final String userid)
	{
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setMessage("Apakah anda yakin ingin menghapus thread ini secara permanen?");
		b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new AsyncTask<String , Integer, String>(){

					ProgressDialog dialog;
					protected void onPreExecute() {
						dialog = ProgressDialog.show(ThreadRoom.this, "", "Please wait ...");
						dialog.show();
					};
					
					@Override
					protected String doInBackground(String... params) {
						// TODO Auto-generated method stub
						String result = "0";
						DBConnection conn = new DBConnection(ThreadRoom.this);
						conn.delete_status_by_id(id);
						String js = URLConnection.getJsonString(URLConnection.URLThreadDelete+"?uid="+userid+"&token="+token+"&sid="+id);
						if(js != null)
						{
							try {
								JSONObject obj = new JSONObject(js);
								if(obj.getString("status").equals("success"))
								{
									result = "1";
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
						return result;
					}
					
					protected void onPostExecute(String result) {
						if(!result.equals("0"))
						{
							this.dialog.dismiss();
							load_data("first");
							Toast.makeText(ThreadRoom.this, "Thread sudah dihapus", Toast.LENGTH_LONG).show();
						}
					};
				}.execute();
			}
		});
		b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).create().show();
	}
	
	private void dialogMessage(String message)
	{
		AlertDialog.Builder b = new AlertDialog.Builder(ThreadRoom.this);
		b.setMessage(message);
		b.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		}).create().show();
	}
	private void btnBack()
	{
		Button btnBack = (Button) findViewById(R.id.thread_btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(ThreadRoom.this,Home.class);
				i.putExtra("module", "thread");
				i.putExtra("r", name);
				i.putExtra("u", uid);
				i.putExtra("t", token);
				i.putExtra("login", "truet");
				startActivity(i);
			}
		});
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK)
    	{
			finish();
			Intent i = new Intent(ThreadRoom.this,Home.class);
			i.putExtra("module", "thread");
			i.putExtra("r", name);
			i.putExtra("u", uid);
			i.putExtra("t", token);
			i.putExtra("login", "truet");
			
			startActivity(i);
    	}
		return super.onKeyDown(keyCode, event);
	}
}
