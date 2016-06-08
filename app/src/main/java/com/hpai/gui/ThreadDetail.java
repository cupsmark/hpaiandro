package com.hpai.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.hpai.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreadDetail extends Activity {

	String imgs;
	String ids;
	String text;
	String time;
	String uids;
	String uname;
	String urlimgs;
	String module;
	String login;
	
	String uid;
	String name;
	String token;
	String urlimages;
	DisplayImageOptions opt;
	ImageLoader loader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thread_detail);
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
					.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
					.bitmapConfig(Config.RGB_565)
					.build();
		
		loader = ImageLoader.getInstance();
		
		load();
		btnBack();
	}


	private void load()
	{
		imgs = getIntent().getExtras().getString("imgs");
		ids = getIntent().getExtras().getString("ids");
		text = getIntent().getExtras().getString("text");
		time = getIntent().getExtras().getString("time");
		uids = getIntent().getExtras().getString("uids");
		uname = getIntent().getExtras().getString("uname");
		urlimgs = getIntent().getExtras().getString("urlimgs");
		module = getIntent().getExtras().getString("module");
		login = getIntent().getExtras().getString("login");
		uid = getIntent().getExtras().getString("uid");
		name = getIntent().getExtras().getString("name");
		token = getIntent().getExtras().getString("token");
		urlimages = getIntent().getExtras().getString("urlimages");
		
		
		TextView textTitle = (TextView) findViewById(R.id.thread_detail_header_title);
		TextView textDesc = (TextView) findViewById(R.id.thread_detail_textdetail);
		TextView textTime = (TextView) findViewById(R.id.thread_detail_text_time);
		ImageView imgDetail = (ImageView) findViewById(R.id.thread_detail_image);
		
		textTitle.setTypeface(Typeface.DEFAULT_BOLD);
		textTitle.setTextColor(Color.parseColor("#000000"));
		textDesc.setTextColor(Color.parseColor("#2f2f2f"));
		textTime.setTextColor(Color.parseColor("#2f2f2f"));
		SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy HH:mm");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
			Date d = format2.parse(time);
			textTime.setText(format.format(d));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
		textTitle.setText(uname);
		textDesc.setText(text);
		
		if(urlimgs.equals("null") || urlimgs.equals(null) || urlimgs.equals("")){
			imgDetail.setImageDrawable(getResources().getDrawable(R.drawable.product_icon));
		}
		else
		{
			
			loader.displayImage(urlimgs, imgDetail,opt);
		}
	}
	
	private void btnBack()
	{
		Button btnBack = (Button) findViewById(R.id.thread_detail_btn_back);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				Intent i = new Intent(ThreadDetail.this,ThreadRoom.class);
				i.putExtra("uid", uid);
				i.putExtra("name", name);
				i.putExtra("token", token);
				i.putExtra("urlimages", urlimages);
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
			Intent i = new Intent(ThreadDetail.this,ThreadRoom.class);
			i.putExtra("uid", uid);
			i.putExtra("name", name);
			i.putExtra("token", token);
			i.putExtra("urlimages", urlimages);
			startActivity(i);
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
