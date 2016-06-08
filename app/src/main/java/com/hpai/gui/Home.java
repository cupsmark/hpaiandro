package com.hpai.gui;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hpai.R;
import com.hpai.connection.URLConnection;
import com.hpai.dbhelper.DBConnection;
import com.hpai.lib.internal.GCMUtilities;
import com.hpai.lib.internal.GeneralLibrary;
import com.hpai.models.MUsers;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;


public class Home extends Activity {
	
	
	String module = "";
	String name = "";
	String uid = "";
	String tokens = "";
	String login = "";
	String intval;
	String path_image = ""; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button_layout();
        menu_layout();
        module = getIntent().getExtras().getString("module");
        login = getIntent().getExtras().getString("login");
        //startService(new Intent(Home.this,BonusNotificationService.class));
        
        Bitmap bmpTopBG = ((BitmapDrawable) getResources().getDrawable(R.drawable.top_intro_bg)).getBitmap();
        ImageView topView = (ImageView) findViewById(R.id.top_home_bg);
        Bitmap newTopBG = GeneralLibrary.resizeFromBitmap(bmpTopBG,width_screen() - 50 , width_screen() - 50);
        topView.setImageBitmap(newTopBG);
     
    }
    
    
    private void menu_layout()
    {
    	
    }
    
    @SuppressWarnings("deprecation")
	private void button_layout()
    {
    	int width = (int) (width_screen() / 1.5); 
    	LinearLayout layout = (LinearLayout) findViewById(R.id.home_btn_container);
    	LayoutParams btnLayout = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
    	btnLayout.gravity = Gravity.CENTER;
    	btnLayout.width = width;
    	btnLayout.bottomMargin = 3;
    	
    	Button btnProfile = new Button(this);
    	btnProfile.setId(R.id.home_btn_profile);
    	btnProfile.setText("Profile");
    	btnProfile.setTextColor(Color.parseColor("#ffffff"));
    	btnProfile.setTypeface(Typeface.DEFAULT_BOLD);
    	btnProfile.setLayoutParams(btnLayout);
    	btnProfile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent iProfile = new Intent(Home.this,GDetailList.class);
				iProfile.putExtra("json", "");
				iProfile.putExtra("module", "profile");
				iProfile.putExtra("login", login);
				startActivity(iProfile);
			}
		});
    	btnProfile.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_home_btn));
    	
    	Button btnProduk = new Button(this);
    	btnProduk.setId(R.id.home_btn_produk);
    	btnProduk.setText("Produk");
    	btnProduk.setTextColor(Color.parseColor("#ffffff"));
    	btnProduk.setTypeface(Typeface.DEFAULT_BOLD);
    	btnProduk.setLayoutParams(btnLayout);
    	btnProduk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Home.this,GParentList.class);
				i.putExtra("title", "Kategori");
				i.putExtra("url", URLConnection.URLCategoryProduk);
				i.putExtra("module", "product");
				i.putExtra("login", login);
				startActivity(i);
			}
		});
    	btnProduk.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_home_btn));
    	
    	Button btnTestimoni = new Button(this);
    	btnTestimoni.setId(R.id.home_btn_testimoni);
    	btnTestimoni.setText("Testimoni");
    	btnTestimoni.setTextColor(Color.parseColor("#ffffff"));
    	btnTestimoni.setTypeface(Typeface.DEFAULT_BOLD);
    	btnTestimoni.setLayoutParams(btnLayout);
    	btnTestimoni.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent j = new Intent(Home.this,GParentList.class);
				j.putExtra("title", "Testimoni");
				j.putExtra("url", URLConnection.URLCategoryTestimoni);
				j.putExtra("module", "testimoni");
				j.putExtra("login", login);
				startActivity(j);
			}
		});
    	btnTestimoni.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_home_btn));
    	
    	Button btnInfo = new Button(this);
    	btnInfo.setId(R.id.home_btn_info);
    	btnInfo.setText("Info HPAI");
    	btnInfo.setTextColor(Color.parseColor("#ffffff"));
    	btnInfo.setTypeface(Typeface.DEFAULT_BOLD);
    	btnInfo.setLayoutParams(btnLayout);
    	btnInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent j = new Intent(Home.this,GParentList.class);
				j.putExtra("title", "Info HPAI");
				j.putExtra("url", URLConnection.URLInfo);
				j.putExtra("module", "info");
				j.putExtra("login", login);
				startActivity(j);
				
				
			}
		});
    	btnInfo.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_home_btn));
    	
    	Button btnMember = new Button(this);
    	btnMember.setId(R.id.home_btn_member);
    	btnMember.setText("Member");
    	btnMember.setTextColor(Color.parseColor("#ffffff"));
    	btnMember.setTypeface(Typeface.DEFAULT_BOLD);
    	btnMember.setLayoutParams(btnLayout);
    	btnMember.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBConnection dbconn = new DBConnection(Home.this);
				List<MUsers> users = dbconn.getInterval();
				for(MUsers users2 : users)
				{
					intval = users2.getInterval();
				}
				
				if((login.equals("truet")) || (login == null) || login.equals(null))
				{
					if(!URLConnection.checkConnection(Home.this)){
						no_connection_dialog();
					}
					else
					{
						DBConnection dbs = new DBConnection(Home.this);
						List<MUsers> user = dbs.userIsLogin();
						for(MUsers us : user)
						{
							name = us.getName();
							uid = us.getUID();
							tokens = us.getToken();
						}
						Intent intent = new Intent(Home.this,MemberActivity.class);
						intent.putExtra("r", name);
						intent.putExtra("u", uid);
						intent.putExtra("t", tokens);
						startActivity(intent);
					}
					
				}
				else
				{
					if(!URLConnection.checkConnection(Home.this))
					{
						no_connection_dialog();
					}
					else
					{
						DBConnection conn = new DBConnection(Home.this);
						List<MUsers> u = conn.userIsLogin();
						if(u.size() > 0)
						{
							for(MUsers us : u)
							{
								name = us.getName();
								uid = us.getUID();
								tokens = us.getToken();
							}
							
							
							
							Intent intent = new Intent(Home.this,MemberActivity.class);
							intent.putExtra("r", name);
							intent.putExtra("u", uid);
							intent.putExtra("t", tokens);
							startActivity(intent);
						}
						else
						{
							show_login_dialog("member");
						}
					}
					
				}
			}
		});
    	btnMember.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_home_btn));
    	
    	Button btnThread = new Button(this);
    	btnThread.setId(R.id.home_btn_thread);
    	btnThread.setText("Thread Room");
    	btnThread.setTextColor(Color.parseColor("#ffffff"));
    	btnThread.setTypeface(Typeface.DEFAULT_BOLD);
    	btnThread.setLayoutParams(btnLayout);
    	btnThread.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DBConnection dbconn = new DBConnection(Home.this);
				List<MUsers> users = dbconn.getInterval();
				for(MUsers users2 : users)
				{
					intval = users2.getInterval();
				}
				
				if((login.equals("truet")) || (login == null) || login.equals(null))
				{
					if(!URLConnection.checkConnection(Home.this)){
						no_connection_dialog();
					}
					else
					{
						DBConnection dbs = new DBConnection(Home.this);
						List<MUsers> user = dbs.userIsLogin();
						for(MUsers us : user)
						{
							name = us.getName();
							uid = us.getUID();
							tokens = us.getToken();
							path_image = us.getAvatar();
						}
						Intent intent = new Intent(Home.this,ThreadRoom.class);
						intent.putExtra("r", name);
						intent.putExtra("u", uid);
						intent.putExtra("t", tokens);
						intent.putExtra("urlimages", path_image);
						startActivity(intent);
					}
					
				}
				else
				{
					if(!URLConnection.checkConnection(Home.this))
					{
						no_connection_dialog();
					}
					else
					{
						DBConnection conn = new DBConnection(Home.this);
						List<MUsers> u = conn.userIsLogin();
						if(u.size() > 0)
						{
							for(MUsers us : u)
							{
								name = us.getName();
								uid = us.getUID();
								tokens = us.getToken();
								path_image = us.getAvatar();
							}
							
							
							
							Intent intent = new Intent(Home.this,ThreadRoom.class);
							intent.putExtra("r", name);
							intent.putExtra("u", uid);
							intent.putExtra("t", tokens);
							intent.putExtra("urlimages", path_image);
							startActivity(intent);
						}
						else
						{
							show_login_dialog("thread");
						}
					}
					
				}
			}
		});
    	btnThread.setBackgroundDrawable(getResources().getDrawable(R.drawable.style_home_btn));
    	
    	layout.addView(btnProfile);
    	layout.addView(btnProduk);
    	layout.addView(btnTestimoni);
    	layout.addView(btnInfo);
    	layout.addView(btnMember);
    	//layout.addView(btnThread);
    }
    
    private void show_login_dialog(final String flag)
    {	
    	final Dialog dialogs = new Dialog(Home.this);
    	dialogs.setContentView(R.layout.login_layout);
    	dialogs.setCancelable(true);
    	
    	String username = "";
    	DBConnection dbs = new DBConnection(Home.this);
    	if(dbs.get_count_user() > 0)
    	{
    		String uid = dbs.getLastUserLogin();
    		if(!uid.equals("001"))
    		{
    			username = dbs.getLastUserLogin();
    		}
    		else
    		{
    			username = "";
    		}
    	}
    	else
    	{
    		username = "";
    	}
    	
    	final EditText txtUsername = (EditText) dialogs.findViewById(R.id.user_name);
    	final EditText txtPassword = (EditText) dialogs.findViewById(R.id.user_password);
    	final TextView txtError = (TextView) dialogs.findViewById(R.id.user_txt_error);
    	txtUsername.setText(username);
    	txtError.setTextSize(12);
    	Button btnLogin = (Button) dialogs.findViewById(R.id.user_btn_login);
    	txtUsername.requestFocus();
    	btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(!URLConnection.checkConnection(Home.this))
				{
					RelativeLayout layout = (RelativeLayout) dialogs.findViewById(R.id.user_error_container);
					txtError.setText("No Internet Connection");
					txtError.setTextColor(Color.RED);
					layout.setVisibility(View.VISIBLE);
				}
				else
				{
					if(txtUsername.getText().toString().equals("") || txtPassword.getText().toString().equals(""))
					{
						RelativeLayout layout = (RelativeLayout) dialogs.findViewById(R.id.user_error_container);
						txtError.setTextColor(Color.RED);
						txtError.setText("Masukkan username dan password");
						layout.setVisibility(View.VISIBLE);
					}
					else
					{
						new AsyncTask<String, Integer, String>(){

							ProgressDialog di = ProgressDialog.show(Home.this, "", "Loading...");
							protected void onPreExecute() {
								di.show();
							};
							
							@Override
							protected String doInBackground(String... params) {
								// TODO Auto-generated method stub
								
								
								
								String json = URLConnection.postJsonLogin(params[0], params[1], params[2]);
								String result = "";
								try {
									JSONObject obj = new JSONObject(json);
									if(obj.getString("results").equals("0"))
									{
										result = "0";
									}
									else
									{
										if(obj.getString("success").equals("true"))
										{
											JSONArray arr = new JSONArray(obj.getString("rows"));
											JSONObject obj2 = arr.getJSONObject(0);
											
											String jsonImage = URLConnection.getJsonString(URLConnection.URLThreadImage+"?token="+obj2.getString("t"));
											JSONObject objImage = new JSONObject(jsonImage);
											JSONArray arrImage = new JSONArray(objImage.getString("rows"));
											JSONObject objImage2 = arrImage.getJSONObject(0);
											String pathImage = objImage2.getString("a");
													
											SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());
											Date date = new Date();
											DBConnection db = new DBConnection(Home.this);
											if(db.check_user_id(obj2.getString("u"))){
												if(db.check_user_id("001"))
												{
													db.delete_user("001");
												}
												
												
												
												db.update_user(new MUsers(obj2.getString("u"), obj2.getString("r"), obj2.getString("t"), "login",dateFormat.format(date),GCMUtilities.deviceId(Home.this),intval,pathImage));
											}
											else
											{
												if(db.check_user_id("001"))
												{
													db.delete_user("001");
												}
												db.insert_user(new MUsers(obj2.getString("u"), obj2.getString("r"), obj2.getString("t"), "login",dateFormat.format(date),GCMUtilities.deviceId(Home.this),intval,pathImage));
											}
											path_image = pathImage;
											result = obj2.getString("u")+"-eko-"+obj2.getString("r")+"-eko-"+obj2.getString("t");
										}
										else
										{
											result = "1";
										}
										
									}
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								return result;
							}
							
							protected void onPostExecute(String result) {
								di.dismiss();
								if((!result.equals("0")) && (!result.equals("1")))
								{
									String[] splitter = result.split("-eko-");
									String u = splitter[0];
									String r = splitter[1];
									String t = splitter[2];
									
								
									
									
									
									
									
									dialogs.dismiss();
									if(flag.equals("member"))
									{
										Intent intent = new Intent(Home.this,MemberActivity.class);
										intent.putExtra("r", r);
										intent.putExtra("u", u);
										intent.putExtra("t", t);
										intent.putExtra("urlimages", path_image);
										startActivity(intent);
									}
									else
									{
										Intent intent = new Intent(Home.this,ThreadRoom.class);
										intent.putExtra("r", r);
										intent.putExtra("u", u);
										intent.putExtra("t", t);
										intent.putExtra("urlimages", path_image);
										startActivity(intent);
									}
									
								}
								else
								{
									RelativeLayout layout = (RelativeLayout) dialogs.findViewById(R.id.user_error_container);
									txtError.setTextColor(Color.RED);
									layout.setVisibility(View.VISIBLE);
									if(result.equals("0"))
									{
										
										txtError.setText("Username atau Password salah");
									}
									
								}
							};
							
						}.execute(new String[]{URLConnection.URLLogin,txtUsername.getText().toString(),txtPassword.getText().toString()});
					}
					
				}
			}
		});    	
    	
    	dialogs.show();
    }
    

   
    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private int width_screen()
    {
    	int width = 0;
    	WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
    	Display display = wm.getDefaultDisplay();
    	if(android.os.Build.VERSION.SDK_INT > 12)
    	{
    		Point size = new Point();
            display.getSize(size);
            width = size.x;
    	}
    	else
    	{
    		width = display.getWidth();
    	}
    	
    	return width;
    }
    
    @SuppressLint("NewApi")
   	@SuppressWarnings("deprecation")
   	private int height_screen()
       {
       	int height = 0;
       	WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
       	Display display = wm.getDefaultDisplay();
       	if(android.os.Build.VERSION.SDK_INT > 12)
       	{
       		Point size = new Point();
               display.getSize(size);
               height = size.y;
       	}
       	else
       	{
       		height = display.getHeight();
       	}
       	
       	return height;
       }
   
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode == KeyEvent.KEYCODE_BACK)
    	{
    		finish();
    		Toast.makeText(Home.this, "Press again to exit", Toast.LENGTH_SHORT).show();
    	}
    	return super.onKeyDown(keyCode, event);
    }

    
    private void no_connection_dialog()
    {
    	AlertDialog.Builder bu = new AlertDialog.Builder(Home.this);
    	bu.setMessage("No Internet Connection");
    	bu.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
    	bu.create().show();
    }
}
