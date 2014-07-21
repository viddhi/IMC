package com.example.imc;


import java.util.Calendar;

import com.example.imc.AlarmReceiver;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.TextView;





public class MainActivity extends FragmentActivity  implements
ActionBar.TabListener {

private ViewPager viewPager;
private TabsPagerAdapter mAdapter;
private ActionBar actionBar;
// Tab titles
private String[] tabs = { "Home","Buzz","About","Explore" };

@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	
	// Set the alarm to start at approximately 9 a.m.
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 9);
    Intent intentAlarm = new Intent(this, AlarmReceiver.class);
    // create the object
    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
	setContentView(R.layout.activity_main);
	
	// Initilization
	viewPager = (ViewPager) findViewById(R.id.pager);
	actionBar = getActionBar();
	mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

	viewPager.setAdapter(mAdapter);
	actionBar.setHomeButtonEnabled(true);
	
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		
	actionBar.setDisplayShowTitleEnabled(false);
	Typeface face=Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf"); 
	// Adding Tabs
	for (String tab_name : tabs) {
		TextView t = new TextView(this);
	    t.setText("\n" + tab_name);
	    t.setTypeface(face);
	    t.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);	    
		actionBar.addTab(actionBar.newTab().setCustomView(t)
				.setTabListener(this));
	}

	/**
	 * on swiping the viewpager make respective tab selected
	 * */
	viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

		@Override
		public void onPageSelected(int position) {
			// on changing the page
			// make respected tab selected
			actionBar.setSelectedNavigationItem(position);
		}
		
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	});
}



@Override
public void onTabReselected(Tab tab, FragmentTransaction ft) {
}

@Override
public void onTabSelected(Tab tab, FragmentTransaction ft) {
	// on tab selected
	// show respected fragment view
	viewPager.setCurrentItem(tab.getPosition());
}

@Override
public void onTabUnselected(Tab tab, FragmentTransaction ft) {
}

}
