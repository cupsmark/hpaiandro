package com.hpai.gui;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.hpai.R;
import com.hpai.connection.URLConnection;
import com.hpai.dbhelper.DBConnection;
import com.hpai.lib.internal.GeneralLibrary;
import com.hpai.models.MGuessCTestimoni;
import com.hpai.models.MGuessProduct;
import com.hpai.models.MGuessProfile;
import com.hpai.models.MGuessTestimoni;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.text.Html;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.graphics.Typeface;

public class GDetailList extends Activity {

	DisplayImageOptions opt;
	ImageLoader loader;
	private String modules;
	
	private ProgressDialog progress;
	String[] path_image;
	String[] desc;
	String image_uri;
	String textDesc;
	String textTitle;
	String cat;
	String login = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gdetail_list);
        
        String json = getIntent().getExtras().getString("json");
        final String module = getIntent().getExtras().getString("module");
        modules = module;
        login = getIntent().getExtras().getString("login");
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
		
        if(module.equals("product") || module.equals("testimoni"))
        {
        	cat = getIntent().getExtras().getString("catid");
        }
        
        
        Button btnBack = (Button) findViewById(R.id.gdetail_btn_back);
        btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				if(module.equals("product"))
				{
					
					Intent i = new Intent(GDetailList.this,GChildList.class);
					i.putExtra("url", URLConnection.URLProduk+"?c="+getIntent().getExtras().getString("catid"));
					i.putExtra("module", "product");
					i.putExtra("title", "Produk");
					i.putExtra("catid", cat);
					i.putExtra("offset", "0");
					i.putExtra("page", "1");
					startActivity(i);
				}
				else if(module.equals("profile"))
				{
					Intent iprofile = new Intent(GDetailList.this,Home.class);
					iprofile.putExtra("module", module);
					iprofile.putExtra("login", login);
					startActivity(iprofile);
				}
				else if(module.equals("info"))
				{
					
				}
				else
				{
					Intent x = new Intent(GDetailList.this,GChildList.class);
					x.putExtra("url", URLConnection.URLTestimoni+"?c="+getIntent().getExtras().getString("catid"));
					x.putExtra("module", "testimoni");
					x.putExtra("title", "Testimoni");
					x.putExtra("catid", cat);
					x.putExtra("offset", "0");
					x.putExtra("page", "1");
					startActivity(x);
				}
				
			}
		});
        
        if(module.equals("product"))
        {
        	productDetail(json);
        }
        else if (module.equals("profile"))
        {
        	profileDetail();
        }
        else if(module.equals("info"))
        {
        	
        }
        else
        {
        	testimoniDetail(json);
        }
        
        ImageButton btnShare = (ImageButton) findViewById(R.id.gdetail_share_button);
        btnShare.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(module.equals("testimoni"))
				{
					DBConnection conn = new DBConnection(GDetailList.this);
					MGuessCTestimoni testi = conn.get_testimoni_category_by_id(Integer.parseInt(cat));
					String[] split = testi.getImageUrl().split("/");
					String image_url = "file:///mnt/sdcard/HPAI/"+split[split.length - 1];
					String text = "Testimoni "+testi.getTitle();
					//share(image_url, textDesc, text);
					popup_choose(image_url, textDesc, text);
				}
				else
				{
					
					popup_choose(image_uri, textDesc, textTitle);
				}
			}
		});
    }

    private void productDetail(String json)
    {
    	try {
    		//int newWidth = (int)(screen_width() /1.5);
    		DBConnection conn = new DBConnection(GDetailList.this);
    		MGuessProduct product = conn.get_product_by_id(Integer.parseInt(json));
    		
			//JSONObject jsonObj = new JSONObject(json);
    		String[] split = product.getImageUri().split("/");
			String image_url = "file:///mnt/sdcard/HPAI/"+split[split.length - 1];
			String desc = product.getDesc();
			image_uri = image_url;
			textDesc = desc;
			textTitle = "Produk HPAI";
			TextView actTitle = (TextView) findViewById(R.id.gdetail_header_title);
			actTitle.setText(product.getTitle());
			actTitle.setTextColor(Color.parseColor("#000000"));
			actTitle.setTypeface(Typeface.DEFAULT_BOLD);
			textTitle = product.getTitle();
			TextView textdetail = (TextView) findViewById(R.id.gdetail_textdetail);
			textdetail.setText(Html.fromHtml(desc));
			textdetail.setTextColor(Color.parseColor("#000000"));
			final ImageView imageView = (ImageView) findViewById(R.id.gdetail_image);
			if(image_url.equals("null") || image_url.equals(null) || image_url.equals("")){
				imageView.setImageDrawable(getResources().getDrawable(R.drawable.product_icon));
			}
			else
			{
				
				loader.displayImage(image_url, imageView,opt);
			}
			conn.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private void testimoniDetail(String json)
    {
    	try {
			
    		DBConnection conn = new DBConnection(GDetailList.this);
    		MGuessTestimoni testimoni = conn.get_testimoni_by_id(Integer.parseInt(json));
    		String[] split = testimoni.getImageUrl().split("/");
			String image_url = "file:///mnt/sdcard/HPAI/"+split[split.length - 1];
			String desc = testimoni.getDesc();
			image_uri = image_url;
			textDesc = desc;
			textTitle = "Testimoni HPAI";
			TextView actTitle = (TextView) findViewById(R.id.gdetail_header_title);
			actTitle.setText(testimoni.getTitle());
			actTitle.setTextColor(Color.parseColor("#000000"));
			actTitle.setTypeface(Typeface.DEFAULT_BOLD);
			textTitle = testimoni.getTitle();
			TextView textdetail = (TextView) findViewById(R.id.gdetail_textdetail);
			textdetail.setText(Html.fromHtml(desc));
			textdetail.setTextColor(Color.parseColor("#000000"));
			ImageView imageView = (ImageView) findViewById(R.id.gdetail_image);
			imageView.setScaleType(ScaleType.CENTER);
			imageView.setAdjustViewBounds(true);
			if(image_url.equals("null") || image_url.equals(null) || image_url.equals("")){
				imageView.setImageDrawable(getResources().getDrawable(R.drawable.product_icon));
			}
			else
			{
				if(checkFile(testimoni.getImageUrl()))
				{
					Bitmap bmps = GeneralLibrary.resizeImage(getPath(testimoni.getImageUrl()), (int)(screen_width() / 1.6), (int)(screen_width() / 1.6));//BitmapFactory.decodeFile(getPath(testimoni.getImageUrl()));
					imageView.setImageBitmap(bmps);
				}
				else
				{
					loader.displayImage(image_url, imageView,opt);
				}
				
			}

			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
   
    private void profileDetail()
    {
    	new AsyncTask<String, Integer, String>() {

    		protected void onPreExecute() {
    			progress = new ProgressDialog(GDetailList.this);
				progress.setMessage("Loading...");
				progress.setCancelable(true);
				progress.setIndeterminate(true);
				progress.show();
				super.onPreExecute();
    		};
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				
				return "";
			}
			
			protected void onPostExecute(String result) {
				progress.dismiss();
				DBConnection connection = new DBConnection(GDetailList.this);
				List<MGuessProfile> prof = connection.get_profile();
				path_image = new String[prof.size()];
				desc = new String[prof.size()];
				int i = 0;
				for(MGuessProfile profile : prof)
				{
					String[] split = profile.getImageUrl().split("/");
					path_image[i] = "file:///mnt/sdcard/HPAI/"+split[split.length - 1];
					desc[i] = profile.getDesc();
					image_uri = path_image[i];
					textDesc = profile.getDesc();
					textTitle = profile.getTitle();
					
					TextView appTitle = (TextView) findViewById(R.id.gdetail_header_title);
					appTitle.setText(profile.getTitle());
					appTitle.setTextColor(Color.parseColor("#000000"));
					appTitle.setTypeface(Typeface.DEFAULT_BOLD);
					TextView textdetail = (TextView) findViewById(R.id.gdetail_textdetail);
					textdetail.setText(Html.fromHtml(profile.getDesc()));
					textdetail.setTextColor(Color.parseColor("#000000"));
					ImageView imageView = (ImageView) findViewById(R.id.gdetail_image);
					if(profile.getImageUrl().equals("null") || profile.getImageUrl().equals(null) || profile.getImageUrl().equals("")){
						imageView.setImageDrawable(getResources().getDrawable(R.drawable.product_icon));
					}
					else
					{
						if(checkFile(profile.getImageUrl()))
						{
							loader.displayImage(path_image[i], imageView,opt);
						}
						else
						{
							loader.displayImage(profile.getImageUrl(), imageView, opt, new SimpleImageLoadingListener(){
								@Override
								public void onLoadingComplete(String imageUri, View view,
										Bitmap loadedImage) {
									// TODO Auto-generated method stub
									String[] split = imageUri.split("/");
									String filename = split[split.length - 1];
									File myDir = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "HPAI" + File.separator);
									if(!myDir.exists())
									{
										myDir.mkdir();
									}
									File file = new File(myDir,filename);
									if(!file.exists()){
										try
										{
											final FileOutputStream _out = new FileOutputStream(file);
											Bitmap _bitmap = Bitmap.createBitmap(loadedImage);
											_bitmap.compress(Bitmap.CompressFormat.PNG, 100, _out);
											_out.flush();
											_out.close();
											
										}
										catch(Exception e)
										{
											e.printStackTrace();
										}
									}
								}
							});
						}
						
					}
					i++;
				}
				

			};
		}.execute(URLConnection.URLProfile);
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
    private String getPath(String url)
	{
		String result = "";
		String[] split = url.split("/");
		String filename = split[split.length - 1];
		result = Environment.getExternalStorageDirectory().getAbsolutePath()+"/HPAI/"+filename;
		return result;
	}
	
    
	private void share(String uri_image,String text,String title)
    {
		try
		{
			List<Intent> targetedShareIntents = new ArrayList<Intent>();
			Intent share = new Intent(android.content.Intent.ACTION_SEND);
    	    share.setType("image/jpeg");
    	    Uri fileImage = Uri.parse(uri_image);
    	    List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(share, 0);
    	    if (!resInfo.isEmpty()){
    	    	for (ResolveInfo info : resInfo) {
    	    		Intent targetedShare = new Intent(android.content.Intent.ACTION_SEND);
    	    		targetedShare.setType("image/jpeg"); // put here your mime type
    	    		targetedShare.putExtra(Intent.EXTRA_SUBJECT, title);
    	    		
    	    		if (!info.activityInfo.packageName.toLowerCase().contains("facebook") || !info.activityInfo.name.toLowerCase().contains("facebook")) {
    	    			targetedShare.putExtra(Intent.EXTRA_TEXT,text);
    	    			targetedShare.putExtra(Intent.EXTRA_STREAM, fileImage );
    	    			targetedShare.setPackage(info.activityInfo.packageName);
    	    			targetedShareIntents.add(targetedShare);
    	    		}
    	    	}
    	    	
    	    	Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "Select app to share");
    	    	chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
    	    	startActivity(chooserIntent);
    	    }
		}
		catch(Exception e){
    	      e.getMessage();
		}
    }
    
    @SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	private int screen_width()
    {
    	int width = 0;
    	//int height = 0;
    	
    	WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
    	Display display = wm.getDefaultDisplay();
    	if(android.os.Build.VERSION.SDK_INT > 12)
    	{
    		Point size = new Point();
            display.getSize(size);
            width = size.x;
            //height = size.y;
    	}
    	else
    	{
    		width = display.getWidth();
    		//height = display.getHeight();
    	}
    	
    	return width;
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode == KeyEvent.KEYCODE_BACK)
    	{
    		finish();
    		if(modules.equals("product"))
			{
				Intent i = new Intent(GDetailList.this,GChildList.class);
				i.putExtra("url", URLConnection.URLProduk+"?c="+cat);
				i.putExtra("module", "product");
				i.putExtra("title", "Produk");
				i.putExtra("catid", cat);
				i.putExtra("offset", "0");
				i.putExtra("page", "1");
				startActivity(i);
				
			}
			else if(modules.equals("profile"))
			{
				Intent iprofile = new Intent(GDetailList.this,Home.class);
				iprofile.putExtra("module", modules);
				iprofile.putExtra("login", login);
				startActivity(iprofile);
				
			}
			else if(modules.equals("info"))
			{
				
			}
			else
			{
				Intent x = new Intent(GDetailList.this,GChildList.class);
				x.putExtra("url", URLConnection.URLTestimoni+"?c="+cat);
				x.putExtra("module", "testimoni");
				x.putExtra("title", "Testimoni");
				x.putExtra("catid", cat);
				x.putExtra("offset", "0");
				x.putExtra("page", "1");
				startActivity(x);
				
			}
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
    private void popup_choose(final String image_url, final String textDesc, final String title)
    {
    	final Dialog dialogs = new Dialog(GDetailList.this);
    	dialogs.setTitle("Share dengan");
    	dialogs.setContentView(R.layout.share_popup_layout);
    	dialogs.setCancelable(true);
    	
    	ImageButton btnfb = (ImageButton) dialogs.findViewById(R.id.share_popup_btn_fb);
    	Button btnLain = (Button) dialogs.findViewById(R.id.share_popup_btn_lain);
    	
    	btnLain.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				share(image_url, textDesc, title);
				dialogs.dismiss();
			}
		});
    	
    	btnfb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialogs.dismiss();
				/*Intent i = new Intent(GDetailList.this,CustomFBLogin.class);
				i.putExtra("image", image_url.replace("file:///mnt", ""));
				i.putExtra("desc", textDesc);
				i.putExtra("title", title);
				startActivity(i);*/
				
			}
		});
    	dialogs.show();
    }
  
}
