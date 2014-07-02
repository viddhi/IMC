package com.example.imc;
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
				/*if(homeFragment == null) {
					homeFragment = new HomeFragment();
				}
				return homeFragment;*/
			}
			case 1:{
				individualFragment = new TheBuzzFragment();
				break;
				/*if(buzzFragment == null){
					buzzFragment = new TheBuzzFragment();
				}
				return buzzFragment;*/
			}
			
			case 2:{
				individualFragment = new AboutIMCFragment();
				break;
				/*if(imcFragment == null){
					imcFragment = new AboutIMCFragment();
				}
				return imcFragment;*/
			}
			case 3:{
				individualFragment = new ExploreIMCFragment();
				break;
				/*if(eimcFragment == null){
					eimcFragment = new ExploreIMCFragment();
				}
				return eimcFragment;*/
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
