package com.example.imc;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
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
