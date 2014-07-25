package com.example.imc;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.imc.GlobalState.TrackerName;
import com.google.android.gms.analytics.Tracker;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;



public class ExploreIMCFragment extends Fragment {
	JSONArray categories = null;
	ImageAdapterForExplore adapter;
	IMCCategory _category = new IMCCategory();
	GridView gridView ;
	boolean isExploreIMCLoaded = false;
	
	@Override
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		ConstUtilities.categoriesLists =  new ArrayList<HashMap<String,Object>>();
		    View v = inflater.inflate(R.layout.fragment_exploreimc, container, false);
		    gridView = (GridView) v.findViewById(R.id.grid_view);
		 // Get tracker.
	        Tracker t = ((GlobalState) getActivity().getApplication()).getTracker(
	            TrackerName.APP_TRACKER);

	        // Set screen name.
	        // Where path is a String representing the screen name.
	        t.setScreenName("/ExploreIMC");

	        gridView.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
	            	HashMap<String, Object> c = new HashMap<String, Object>();
	            	c = ConstUtilities.categoriesLists.get(position);
	                Intent i = new Intent(getActivity(), CategoriesPostList.class);
	                // passing array index
	                i.putExtra("CategoryName", c.get(ConstUtilities.Node_CategoryName).toString());
	                i.putExtra("Slug", c.get("slug").toString());
	                startActivity(i);
	            }
	        });
		    
       
		    return v;
	    }
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
	    super.setUserVisibleHint(isVisibleToUser);
	    if (isVisibleToUser && !isExploreIMCLoaded ) {
	    	new CallAPI().execute();
	    	isExploreIMCLoaded = true;
	    }
	}
	
	private class CallAPI extends AsyncTask<Void, Void, Void> {
		ProgressDialog Asycdialog = new ProgressDialog(getActivity());
		 @Override
	     protected void onPreExecute() {

	         super.onPreExecute();
	         Asycdialog.setMessage("Loading...");
	         Asycdialog.show();
	     }
	@Override
	protected Void doInBackground(Void...arg0) {
		try
		{
			 //postLists = ServerUtility.returnIMCPosts();
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    HttpEntity httpEntity = null;
	    HttpResponse httpResponse = null;
	    HttpGet httpGet = new HttpGet(ConstUtilities.getCategoriesDetail);
	    
	    httpResponse = httpClient.execute(httpGet);
	    httpEntity = httpResponse.getEntity();
	    String response = EntityUtils.toString(httpEntity);
	    
	    JSONObject jObj = new JSONObject(response);
	    
	    categories = jObj.getJSONArray("categories");
	    for (int i=0;i<categories.length();i++)
	    {
	    JSONObject singleObj = categories.getJSONObject(i);
	    
	    _category.CategoryID = Integer.parseInt(singleObj.getString(ConstUtilities.Node_CategoryID));
	    _category.CategoryName = singleObj.getString(ConstUtilities.Node_CategoryName);
	    _category.PostCount = singleObj.getString(ConstUtilities.Node_PostCount);
	    _category.Slug = singleObj.getString("slug");
	   
	    HashMap<String,Object> category = new HashMap<String,Object>();
	    category.put(ConstUtilities.Node_CategoryID,  _category.CategoryID);
	    category.put(ConstUtilities.Node_CategoryName, _category.CategoryName);
	    category.put(ConstUtilities.Node_PostCount, _category.PostCount);
	    category.put("slug", _category.Slug);
	
	   
	    ConstUtilities.categoriesLists.add(category);
	    }
	   
	    
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
	    return null;
	}
	    @Override
	    protected void onPostExecute(Void result) {
	    	super.onPostExecute(result);
	        adapter=new ImageAdapterForExplore(getActivity(), ConstUtilities.categoriesLists);
	       gridView.setAdapter(adapter);
	       gridView.setBackgroundColor(Color.WHITE);
	        super.onPostExecute(result);
	        Asycdialog.dismiss();
	    	

	    }


	    } // end CallAPI 


}