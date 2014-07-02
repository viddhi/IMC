package com.example.imc;



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
		    View v = inflater.inflate(R.layout.frament_aboutimc, container, false);
		    WebView wv = (WebView) v.findViewById(R.id.aboutIMCView);
		    wv.loadUrl("file:///android_asset/aboutimc.html");
		    return v;
	    }

}
