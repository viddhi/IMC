package com.example.imc;



import com.example.imc.GlobalState.TrackerName;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class AboutIMCFragment extends Fragment {
	
	@Override
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {		
		// Get tracker.
        Tracker t = ((GlobalState) getActivity().getApplication()).getTracker(
            TrackerName.APP_TRACKER);

        // Set screen name.
        // Where path is a String representing the screen name.
        t.setScreenName("/AboutIMC");

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
		    View v = inflater.inflate(R.layout.frament_aboutimc, container, false);
		    WebView wv = (WebView) v.findViewById(R.id.aboutIMCView);
		    wv.loadUrl("file:///android_asset/aboutimc.html");
		    return v;
	    }

}
