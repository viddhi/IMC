package com.example.imc;

import android.app.ActionBar.Tab;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends FragmentActivity implements
ActionBar.TabListener {

private ViewPager viewPager;
private TabsPagerAdapter mAdapter;
private ActionBar actionBar;
// Tab titles
private String[] tabs = { "Home", "The Buzz", "Profile","About IMC" };

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.activity_main);

// Initialization
viewPager = (ViewPager) findViewById(R.id.pager);
actionBar = getActionBar();
mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

viewPager.setAdapter(mAdapter);
actionBar.setHomeButtonEnabled(false);
actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);		

// Adding Tabs
for (String tab_name : tabs) {
	actionBar.addTab(actionBar.newTab().setText(tab_name)
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

/* Initiating Menu XML file (menu.xml) */
@Override
public boolean onCreateOptionsMenu(Menu menu)
{
    MenuInflater menuInflater = getMenuInflater();
    menuInflater.inflate(R.layout.menu, menu);
    return true;
}

/**
 * Event Handling for Individual menu item selected
 * Identify single menu item by it's id
 * */
@Override
public boolean onOptionsItemSelected(MenuItem item)
{
    
    switch (item.getItemId())
    {
    case R.id .menu_Home:
    	// Single menu item is selected do something
    	// Ex: launching new activity/screen or show alert message
        Toast.makeText(MainActivity.this, "Home is Selected", Toast.LENGTH_SHORT).show();
        return true;
    case R.id.menu_thebuzz:
    	Toast.makeText(MainActivity.this, "The Buzz is Selected", Toast.LENGTH_SHORT).show();
        return true;
    case R.id.menu_Profile:
    	Toast.makeText(MainActivity.this, "Profile is Selected", Toast.LENGTH_SHORT).show();
        return true;
    case R.id.menu_aboutIMC:
    	Toast.makeText(MainActivity.this, "AboutIMC is Selected", Toast.LENGTH_SHORT).show();
    
    default:
        return super.onOptionsItemSelected(item);
    }
}



}
