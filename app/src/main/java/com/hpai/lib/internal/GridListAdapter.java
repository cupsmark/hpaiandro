package com.hpai.lib.internal;



import java.lang.ref.WeakReference;

import com.hpai.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridListAdapter extends BaseAdapter {
    
    private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null; 
    private String[] title;
    private String[] desc;

    private Context context;
    
    public GridListAdapter(Activity a, String[] d, String[] title,String[] desc,Context context) {
        activity = a;
        data=d;
        this.title = title;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.desc = desc;
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_notif, null);

        TextView textTitle =(TextView)vi.findViewById(R.id.textTitle);
        TextView textDesc =(TextView)vi.findViewById(R.id.textDesc);
        final ImageView image=(ImageView)vi.findViewById(R.id.image_notif);
        textTitle.setText(title[position]);
        textTitle.setTypeface(Typeface.DEFAULT_BOLD);
        textDesc.setText(desc[position]);
        
        //Bitmap bmp = BitmapFactory.decodeFile(data[position]);
        //image.setImageBitmap(bmp);
        if(data[position].equals(""))
        {
        	image.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
        }
        else
        {
        	loadBitmap(data[position], image);
        }
        
        return vi;
    }
    
    public void loadBitmap(String data,ImageView imageView)
    {
    	if (cancelPotentialWork(data, imageView)) {
            final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
            Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
            final AsyncDrawable asyncDrawable =new AsyncDrawable(context.getResources(), bmp, task);
            imageView.setImageDrawable(asyncDrawable);
            task.execute(data);
        }
    	
    }
    
    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
	    private final WeakReference<ImageView> imageViewReference;
	    private String data = "";

	    public BitmapWorkerTask(ImageView imageView) {
	        // Use a WeakReference to ensure the ImageView can be garbage collected
	        imageViewReference = new WeakReference<ImageView>(imageView);
	    }

	    // Decode image in background.
	    @Override
	    protected Bitmap doInBackground(String... params) {
	        data = params[0];
	        return BitmapFactory.decodeFile(data);
	    }

	    // Once complete, see if ImageView is still around and set bitmap.
	    @Override
	    protected void onPostExecute(Bitmap bitmap) {
	    	
	    	if (isCancelled()) {
	            bitmap = null;
	        }

	        if (imageViewReference != null && bitmap != null) {
	            final ImageView imageView = imageViewReference.get();
	            final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
	            if (this == bitmapWorkerTask && imageView != null) {
	                imageView.setImageBitmap(bitmap);
	            }
	        }

	    }
	}
    
    
    static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        
		public AsyncDrawable(Resources res,Bitmap bitmap,BitmapWorkerTask bitmapWorkerTask) {
            super(res,bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }
    
    public static boolean cancelPotentialWork(String data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.data;
            if (!bitmapData.equalsIgnoreCase(data)) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }
    
    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
    	   if (imageView != null) {
    	       final Drawable drawable = imageView.getDrawable();
    	       if (drawable instanceof AsyncDrawable) {
    	           final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
    	           return asyncDrawable.getBitmapWorkerTask();
    	       }
    	    }
    	    return null;
    }
    
    
}
