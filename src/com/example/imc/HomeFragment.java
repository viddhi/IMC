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

import android.support.v4.app.ListFragment;
import android.text.Html;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


public class HomeFragment extends ListFragment {
	JSONArray posts = null;
	//Array list of post lists
	ArrayList<HashMap<String,Object>> postLists;
	IMCPost _post = new IMCPost();
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		postLists = new ArrayList<HashMap<String,Object>>();
		
		View rootView = inflater.inflate(R.layout.fragment_home, container, false);
		new CallAPI().execute(); 
		return rootView;
  
}
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	  
	    ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
    	@SuppressLint("SimpleDateFormat")
		@Override
        public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
    		String PostID = ((TextView) arg1.findViewById(R.id.ID)).getText().toString();
    		String Title = ((TextView) arg1.findViewById(R.id.Title)).getText().toString();
    		String DatePosted = ((TextView) arg1.findViewById(R.id.DatePosted)).getText().toString();
    		String Content = ((TextView)arg1.findViewById(R.id.Content)).getText().toString();
    		String Author = ((TextView)arg1.findViewById(R.id.Author)).getText().toString();
    		String Image = ((TextView)arg1.findViewById(R.id.FeaturedImage)).getText().toString();
    		Intent in = new Intent(getActivity(),SinglePostActivity.class);
    		in.putExtra(ConstUtilities.Node_ID, PostID);
    		in.putExtra(ConstUtilities.Node_Title, Title);
    		in.putExtra(ConstUtilities.Node_Date, DatePosted);
    		in.putExtra(ConstUtilities.Node_Content, Content);
    		in.putExtra(ConstUtilities.Node_Author, Author);
    		in.putExtra(ConstUtilities.Node_Image, Image);
    		startActivity(in);
    		
    	}
    	
    });
		
	  }

private class CallAPI extends AsyncTask<Void, Void, Void> {
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
    _post.LikeCount = "Likes: " + singleObj.getString(ConstUtilities.Node_LikeCnt);
    _post.PostID = singleObj.getString(ConstUtilities.Node_ID);
    _post.CommentCount = "Comments: " + singleObj.getString(ConstUtilities.Node_CommentCnt);
    _post.dateposted = singleObj.getString(ConstUtilities.Node_Date);
    _post.Content = singleObj.getString(ConstUtilities.Node_Content);
    JSONObject authorObj  = singleObj.getJSONObject(ConstUtilities.Node_Author);
    _post.Author = authorObj.getString("name").toString();
   
  
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
   
    postLists.add(post);
    }
   
    
	}
	
	catch(Exception ex)
	{
		ex.printStackTrace();
		
	}
    return null;
	
}


protected void onPostExecute(Void result) {
	super.onPostExecute(result);
	ListAdapter adapter = new SimpleAdapter(getActivity(),postLists,
			R.layout.postlist_item,
			new String[] {ConstUtilities.Node_Title,ConstUtilities.Node_Excerpt,ConstUtilities.Node_Image,ConstUtilities.Node_LikeCnt,ConstUtilities.Node_CommentCnt,ConstUtilities.Node_ID,ConstUtilities.Node_Date,ConstUtilities.Node_Content,ConstUtilities.Node_Author},
			new int[] {R.id.Title,R.id.Excerpt,R.id.FeaturedImage,R.id.LikeCnt,R.id.CmtCnt,R.id.ID,R.id.DatePosted,R.id.Content,R.id.Author});
	setListAdapter(adapter);
	

}


} // end CallAPI 



}












