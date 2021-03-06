package com.imc.mobile.android;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.imc.mobile.android.R;
import com.imc.mobile.android.MainActivity;

@SuppressLint("SimpleDateFormat")
public class AlarmReceiver extends BroadcastReceiver
{
         @Override
            public void onReceive(Context context, Intent intent)
            {
        	
        	 //Shared preference
 	        SharedPreferences prefs = context.getSharedPreferences("com.imc.mobile.android", Context.MODE_PRIVATE);
 	        String dateTimeKey = "com.imc.mobile.android.datetime";
 	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ");
 	        Calendar cal = Calendar.getInstance();
 	        String storedDateTime = prefs.getString(dateTimeKey, null);
 	       try
      	 {
 	        if(storedDateTime == null)
 	        {
 	        	storedDateTime = dateFormat.format(cal.getTime());
 	        }

 	        int foundPost = ServerUtility.returnPostCount(storedDateTime);
 	      if(foundPost > 0)
 	        {
 	        	NotificationCompat.Builder mBuilder =
 	        	        new NotificationCompat.Builder(context)
 	        	        .setSmallIcon(R.drawable.ic_launcher)
 	        	        .setContentTitle("IMConnect")
 	        	        .setContentText("IMC has " + foundPost + " new post(s) for you").setAutoCancel(true);
 	        	// Creates an explicit intent for an Activity in your app
 	        	Intent resultIntent = new Intent(context, MainActivity.class);
 	        	
 	        	// The stack builder object will contain an artificial back stack for the
 	        	// started Activity.
 	        	// This ensures that navigating backward from the Activity leads out of
 	        	// your application to the Home screen.
 	        	TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
 	        	// Adds the back stack for the Intent (but not the Intent itself)
 	        	stackBuilder.addParentStack(MainActivity.class);
 	        	// Adds the Intent that starts the Activity to the top of the stack
 	        	stackBuilder.addNextIntent(resultIntent);
 	        	PendingIntent resultPendingIntent =
 	        	        stackBuilder.getPendingIntent(
 	        	            0,
 	        	            PendingIntent.FLAG_UPDATE_CURRENT
 	        	        );
 	        	mBuilder.setContentIntent(resultPendingIntent);
 	        	NotificationManager mNotificationManager =
 	        	    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
 	        	int mId = 1001;
				// mId allows you to update the notification later on.
 	        	mNotificationManager.notify(mId, mBuilder.build());
 	        	
 	      }
 	       prefs.edit().putString(dateTimeKey, dateFormat.format(cal.getTime())).apply();
 	      
 	       
             
            }
         catch(Exception ex)
         {
        	 prefs.edit().putString(dateTimeKey, dateFormat.format(cal.getTime())).apply();
         }
      
}
}
