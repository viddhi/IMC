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

import android.support.v4.app.ListFragment;
import android.text.Html;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class HomeFragment extends ListFragment {
	JSONArray posts = null;
	//Array list of post lists
	ListView list;
	IMCPostAdapter adapter;
	IMCPost _post = new IMCPost();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
	{
		// Get tracker.
        Tracker t = ((GlobalState) getActivity().getApplication()).getTracker(
            TrackerName.APP_TRACKER);

        // Set screen name.
        // Where path is a String representing the screen name.
        t.setScreenName("/Home");
		ConstUtilities.postLists =  new ArrayList<HashMap<String,Object>>();
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		 new CallAPI().execute(); 
		return rootView;
  
}
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	  
	    ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
		@Override
        public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
    		String PostID = ((TextView) arg1.findViewById(R.id.ID)).getText().toString();
    		Intent in = new Intent(getActivity(),SinglePostActivity.class);
    		in.putExtra(ConstUtilities.Node_ID, PostID);
    		in.putExtra(ConstUtilities.Position, String.valueOf(position));
    		startActivity(in);
    		
    	}
    	
    });
		
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
    HttpGet httpGet = new HttpGet(ConstUtilities.getPosts);
    
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
    adapter=new IMCPostAdapter(getActivity(), ConstUtilities.postLists);
    list.setAdapter(adapter);
    super.onPostExecute(result);
    Asycdialog.dismiss();
	

}


} // end CallAPI 



}












