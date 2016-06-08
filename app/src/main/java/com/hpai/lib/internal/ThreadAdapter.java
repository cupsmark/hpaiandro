package com.hpai.lib.internal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.hpai.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ThreadAdapter extends BaseAdapter {
    
    private ArrayList<String> image;
    private static LayoutInflater inflater=null; 
    private ArrayList<String> title;
    private ArrayList<String> desc;
    private ArrayList<String> time;
   // private Context context;
    DisplayImageOptions opt;
	ImageLoader loader;
    
	public ThreadAdapter(Context context)
	{
		this.image = new ArrayList<String>();
        this.title = new ArrayList<String>();
        this.desc = new ArrayList<String>();
        time = new ArrayList<String>();
		inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
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

		
	}
    public ThreadAdapter(ArrayList<String> image, ArrayList<String> title,ArrayList<String> desc,Context context) {
        this.image = image;
        this.title = title;
        this.desc = desc;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
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
		//this.notifyDataSetChanged();
    }

    public void add(String img,String title,String text,String time)
    {
    	image.add(img);
    	this.title.add(title);
    	desc.add(text);
    	this.time.add(time);
    }
    public int getCount() {
        return image.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.list_product_style, null);
        }
        holder = new ViewHolder();
        holder.imageView = (ImageView) convertView.findViewById(R.id.list_product_image);
        holder.textView = (TextView) convertView.findViewById(R.id.list_product_title);;
        holder.textDesc = (TextView) convertView.findViewById(R.id.list_product_desc);;
        holder.textTime = (TextView) convertView.findViewById(R.id.list_product_time);;
        holder.textView.setText(title.get(position));
        holder.textView.setTextColor(Color.parseColor("#2f2f2f"));
        holder.textDesc.setText(desc.get(position));
        holder.textDesc.setTextColor(Color.parseColor("#2f2f2f"));
        SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy HH:mm");
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
			Date d = format2.parse(time.get(position));
			holder.textTime.setText(format.format(d));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        convertView.setTag(holder);
        loader.displayImage(image.get(position), holder.imageView,opt);
        
        return convertView;
    }
    
    class ViewHolder{
		ImageView imageView;
		TextView textView;
		TextView textDesc;
		TextView textTime;
	}
    
}