package com.hpai.lib.internal;


import java.io.File;
import java.io.FileOutputStream;
import com.hpai.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap.Config;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterGParent extends BaseAdapter{
	
	private String[] data;
	private String[] title;
	private Activity a;
	private static LayoutInflater inflater = null;
	DisplayImageOptions opt;
	ImageLoader loader;
	
	public AdapterGParent(Activity a, String[] d, String[] title,Context context)
	{
		this.a = a;
        data = d;
        this.title = title;
        inflater = (LayoutInflater)this.a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.denyCacheImageMultipleSizesInMemory()
		.discCacheFileNameGenerator(new Md5FileNameGenerator())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.build();
		ImageLoader.getInstance().init(config);
		
		opt = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.product_icon)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.bitmapConfig(Config.RGB_565)
				.build();

		loader = ImageLoader.getInstance();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if(convertView == null)
		{
			convertView = inflater.inflate(R.layout.g_parent_list, null);
		}
		holder = new ViewHolder();
		
		ImageView imageView = (ImageView) convertView.findViewById(R.id.gparent_thumbnail);
		TextView textTitle = (TextView) convertView.findViewById(R.id.gparent_title);
		textTitle.setText(title[position]);
		textTitle.setTextColor(Color.parseColor("#000000"));
		if(data[position].equals(null) || data[position].equals("null") || data[position].equals("")){
			imageView.setImageDrawable(a.getResources().getDrawable(R.drawable.product_icon));
		}
		holder.textView = textTitle;
		holder.imageView = imageView;
		convertView.setTag(holder);
		if(checkFile(data[position]))
		{
			loader.displayImage(getPath(data[position]), holder.imageView,opt);
		}
		else
		{
			loader.displayImage(data[position], holder.imageView, opt, new SimpleImageLoadingListener(){
				@Override
				public void onLoadingComplete(String imageUri, View view,
						Bitmap loadedImage) {
					// TODO Auto-generated method stub
					//super.onLoadingComplete(imageUri, view, loadedImage);
					
					String[] split = imageUri.split("/");
					String filename = split[split.length - 1];
					File myDir = new File(Environment.getExternalStorageDirectory().getPath() + File.separator + "HPAI" + File.separator);
					if(!myDir.exists())
					{
						myDir.mkdir();
					}
					File file = new File(myDir,filename);
					if(!file.exists())
					{
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
		
		return convertView;
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
		result = "file://"+Environment.getExternalStorageDirectory().getPath()+"/HPAI/"+filename;
		return result;
	}
	
	class ViewHolder{
		ImageView imageView;
		TextView textView;
	}
}
