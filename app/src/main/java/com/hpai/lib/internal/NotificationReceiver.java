package com.hpai.lib.internal;

import java.util.List;

import com.hpai.dbhelper.DBConnection;
import com.hpai.models.MUsers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
			DBConnection conn = new DBConnection(context);
			List<MUsers> u = conn.userIsLogin();
			if(u.size() > 0)
			{
				String token = "";
				for(MUsers us : u)
				{
					token = us.getToken();
				} 
				
				Intent myIntent = new Intent(context.getApplicationContext(), BonusNotificationService.class);
				myIntent.setAction("run");
				myIntent.putExtra("token", token);
				PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), 0, myIntent, 0);
				AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 180000, pendingIntent);
			}
			else
			{
				Intent myIntent = new Intent(context.getApplicationContext(), BonusNotificationService.class);
				myIntent.setAction("run");
				myIntent.putExtra("token", "null");
				PendingIntent pendingIntent = PendingIntent.getService(context.getApplicationContext(), 0, myIntent, 0);
				AlarmManager alarmManager = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 180000, pendingIntent);
			}
		}
		
	}

}
