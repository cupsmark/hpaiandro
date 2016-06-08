package com.hpai.lib.internal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GeneralLibrary {
	
	Context context;
	public GeneralLibrary(Context c)
	{
		this.context = c;
	}
	
	public boolean check_flag_file()
	{
		boolean result = false;
		File dir = context.getFilesDir();
		String filename = "x.txt";
		File file = new File(dir,filename);
		if(file.exists())
		{
			result = false;
		}
		else
		{
			result = true;
		}
		return result;
	}
	
	public void save_flag()
	{
		String text = "1";
		File dir = context.getFilesDir();
		String filename = "x.txt";
		File file = new File(dir,filename);
		try {
			file.createNewFile();
			file.mkdir();
			FileOutputStream out = new FileOutputStream(file);
			out.write(text.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Bitmap resizeImage(String file, int maxWidth, int maxHeight){
        Bitmap bm = BitmapFactory.decodeFile(file);
        
        int bmpWidth = bm.getWidth();
        int bmpHeight = bm.getHeight();
        
        int newWidth = 0;
        int newHeight = 0;
        
        if(bmpWidth < maxWidth)
        {
        	newWidth = bmpWidth;
        	newHeight = bmpHeight;
        	
        }
        else
        {
        	if(bmpWidth > bmpHeight)
        	{
        		double ratio = ((double) maxWidth) / bmpWidth;
        		newWidth = (int) (ratio * bmpWidth);
        		newHeight = (int) (ratio * bmpHeight);
        	}
        	else
        	{
        		double ratio = ((double) maxHeight) / bmpHeight;
        		newWidth = (int) (ratio * bmpWidth);
        		newHeight = (int) (ratio * bmpHeight);
        	}
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
     return resizedBitmap;
 } 
	
	public static Bitmap resizeFromBitmap(Bitmap bm, int maxWidth, int maxHeight){
        
        int bmpWidth = bm.getWidth();
        int bmpHeight = bm.getHeight();
        
        int newWidth = 0;
        int newHeight = 0;
        
        if(bmpWidth < maxWidth)
        {
        	newWidth = bmpWidth;
        	newHeight = bmpHeight;
        	
        }
        else
        {
        	if(bmpWidth > bmpHeight)
        	{
        		double ratio = ((double) maxWidth) / bmpWidth;
        		newWidth = (int) (ratio * bmpWidth);
        		newHeight = (int) (ratio * bmpHeight);
        	}
        	else
        	{
        		double ratio = ((double) maxHeight) / bmpHeight;
        		newWidth = (int) (ratio * bmpWidth);
        		newHeight = (int) (ratio * bmpHeight);
        	}
        }
        /*Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        Bitmap cs = null;
        cs = Bitmap.createBitmap(newWidth, newHeight, Config.ARGB_8888);
        Canvas canvas = new Canvas(cs);
        canvas.drawBitmap(bm, null, new Rect(0,0,newWidth,newHeight), null);*/
        
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
     return resizedBitmap;
	}
}
