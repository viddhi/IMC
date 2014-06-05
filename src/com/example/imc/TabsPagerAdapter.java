package com.example.imc;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:			
			return new HomeFragment();
		
		case 1:
			return new TheBuzzFragment();
		case 2:
			return new ProfileFragment();
		case 3:
			return new AboutIMCFragment();
		case 4:
			return new ExploreIMCFragment();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}
