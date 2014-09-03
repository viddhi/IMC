package com.imc.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.imc.mobile.android.R;
import com.imc.mobile.android.GlobalState.TrackerName;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CategoriesPostList extends ListActivity {
	JSONArray posts = null;
	//Array list of post lists
	ListView list;
	IMCPostAdapter adapter;
	IMCPost _post = new IMCPost();
	String CategoryName = "";
	String Slug="";
	public Typeface face;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
     // Get tracker.
        Tracker t = ((GlobalState)this.getApplication()).getTracker(TrackerName.APP_TRACKER);
        // Set screen name.Where path is a String representing the screen name.
        t.setScreenName("/CategoriesPostListFromExplore");
        t.send(new HitBuilders.AppViewBuilder().build());
        ConstUtilities.FromExplore = true;
        setContentView(R.layout.activity_categoriespostlist);
        ConstUtilities.postLists =  new ArrayList<HashMap<String,Object>>();
        Intent in = getIntent();
         CategoryName = in.getStringExtra("CategoryName");
         Slug = in.getStringExtra("Slug");
         face=Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"); 
        ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
		@Override
        public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
    		String PostID = ((TextView) arg1.findViewById(R.id.ID)).getText().toString();
    		Intent in = new Intent(getApplicationContext(),SinglePostActivity.class);
    		in.putExtra(ConstUtilities.Node_ID, PostID);
    		in.putExtra(ConstUtilities.Position, String.valueOf(position));
    		startActivity(in);
    		
    	}
    	
    });
		 
        TextView txtView1=(TextView)findViewById(R.id.CategoryName);
        txtView1.setTypeface(face);
        txtView1.setText(CategoryName);
        
        //Buzz on click
        face=Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf"); 
        TextView txtView2=(TextView)findViewById(R.id.Header2);
        txtView2.setTypeface(face);
        txtView2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        txtView2.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
		
		
		new CallAPI().execute(); 
		
	  }
	private class CallAPI extends AsyncTask<Void, Void, Void> {
		ProgressDialog Asycdialog = new ProgressDialog(CategoriesPostList.this);
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
			
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    HttpEntity httpEntity = null;
	    HttpResponse httpResponse = null;
	    String Url = ConstUtilities.getPostByCategories +  Slug;
	    HttpGet httpGet = new HttpGet(Url);
	    
	    httpResponse = httpClient.execute(httpGet);
	    httpEntity = httpResponse.getEntity();
	    String response = EntityUtils.toString(httpEntity);
	    
	    JSONObject jObj = new JSONObject(response);
	    
	    posts = jObj.getJSONArray(ConstUtilities.Node_Posts);
	    for (int i=0;i<posts.length();i++)
	    {
	    JSONObject singleObj = posts.getJSONObject(i);
	    //Title Data Cleaning
	    String tempTitle = singleObj.getString(ConstUtilities.Node_Title);
	    tempTitle = UtilFunctions.stringCleanup(tempTitle);
	    _post.Title = Html.fromHtml(Html.fromHtml((String) tempTitle).toString());
	    String excerptData = singleObj.getString(ConstUtilities.Node_Excerpt);
	    excerptData = UtilFunctions.stringCleanup(excerptData);
	    _post.Excerpt = Html.fromHtml(Html.fromHtml((String) excerptData).toString());
	    _post.Image = singleObj.getString(ConstUtilities.Node_Image).toString();
	    
	    _post.PostID = singleObj.getString(ConstUtilities.Node_ID);
	    _post.CommentCount = "Comments: " + singleObj.getString(ConstUtilities.Node_CommentCnt);
	    _post.dateposted = singleObj.getString(ConstUtilities.Node_Date);
	    _post.Content = singleObj.getString(ConstUtilities.Node_Content);
	    _post.PostUrl = singleObj.getString(ConstUtilities.Node_PostUrl);
	    JSONObject authorObj  = singleObj.getJSONObject(ConstUtilities.Node_Author);
	    _post.Author = authorObj.getString("name").toString();
	    String ZUrl = "http://www.indianmomsconnect.com/?json=get_zilla_likes&id=" + _post.PostID;
	    httpGet = new HttpGet(ZUrl);
	    
	    httpResponse = httpClient.execute(httpGet);
	    httpEntity = httpResponse.getEntity();
	    String response2 = EntityUtils.toString(httpEntity);
	    JSONObject jObj2 = new JSONObject(response2);
	    _post.ZillaLikeCnt = jObj2.getString("0").toString();
	    JSONObject jObj3 = new JSONObject( _post.ZillaLikeCnt);
	    _post.ZillaLikeCnt = jObj3.getString("meta_value").toString();
	    _post.LikeCount = "Likes: " +  _post.ZillaLikeCnt;
	  
	    HashMap<String,Object> post = new HashMap<String,Object>();
	    post.put(ConstUtilities.Node_Title, _post.Title);
	    post.put(ConstUtilities.Node_Excerpt, _post.Excerpt);
	    post.put(ConstUtilities.Node_Image, _post.Image);
	    post.put(ConstUtilities.Node_LikeCnt, _post.LikeCount);
	    post.put(ConstUtilities.Node_CommentCnt, _post.CommentCount);
	    post.put(ConstUtilities.Node_ID, _post.PostID);
	    post.put(ConstUtilities.Node_Date, _post.dateposted);
	    post.put(ConstUtilities.Node_Content, _post.Content);
	    post.put(ConstUtilities.Node_Author,_post.Author);
	    post.put(ConstUtilities.Zilla_Like,  _post.ZillaLikeCnt);
	    post.put(ConstUtilities.Node_PostUrl,_post.PostUrl);
	   
	    ConstUtilities.postLists.add(post);
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
		list = getListView();
	    adapter= new IMCPostAdapter(CategoriesPostList.this, ConstUtilities.postLists);
	    list.setAdapter(adapter);
	    Asycdialog.dismiss();
		

	}


	} // end CallAPI 



	}



