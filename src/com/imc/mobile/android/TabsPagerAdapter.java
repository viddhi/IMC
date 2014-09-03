package com.imc.mobile.android;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsPagerAdapter extends FragmentPagerAdapter {
	
	HomeFragment homeFragment;
	TheBuzzFragment buzzFragment;
	AboutIMCFragment imcFragment;
	ExploreIMCFragment eimcFragment;
	
	Fragment individualFragment;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) 
		{
			case 0:	{
				individualFragment = new HomeFragment();
				break;
				
			}
			case 1:{
				individualFragment = new TheBuzzFragment();
				break;
				
			}
			
			case 2:{
				individualFragment = new AboutIMCFragment();
				break;
				
			}
			case 3:{
				individualFragment = new ExploreIMCFragment();
				break;
				
			}
			default: {
				individualFragment = new HomeFragment();
			}
		}
		return individualFragment;

		
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
