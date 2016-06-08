package com.hpai.lib.internal;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public final class GCMUtilities {
	public static final String SENDER_ID = "173050714273";
	public static final String EXTRA_MESSAGE = "message";
	public static final String DISPLAY_MESSAGE_ACTION ="com.google.android.c2dm.intent.RECEIVE";
//	public static final String SERVER_URL = "http://api.hpai.co.id/notify";
	public static final String SERVER_URL = "http://cupslice.com/hpai/index.php/home/";
	
	public static String regID = "";

	
	
	public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
	
	public static String deviceId(Context context)
	{
		String device_id = "";
		/*TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		device_id = manager.getDeviceId();*/
		
		device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
		return device_id;
	}
	
	
}
