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
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;


public class TheBuzzFragment extends ListFragment {
	JSONArray comments = null;
	//Array list of comments 
	ArrayList<HashMap<String,Object>> commentList;
	IMCComment _cmt = new IMCComment();
	ListView list;
	IMCCmtAdapter adapter;
	View rootView;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		commentList = new ArrayList<HashMap<String,Object>>();
		 rootView = inflater.inflate(R.layout.fragment_thebuzz, container, false);
		 
		new CallAPI().execute(); 
		return rootView;
	}
	
 
	
	private class CallAPI extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void...arg0) {
			try
			{
			
		    DefaultHttpClient httpClient = new DefaultHttpClient();
		    HttpEntity httpEntity = null;
		    HttpResponse httpResponse = null;
		    HttpGet httpGet = new HttpGet(ConstUtilities.getComments);
		    
		    httpResponse = httpClient.execute(httpGet);
		    httpEntity = httpResponse.getEntity();
		    String response = EntityUtils.toString(httpEntity);
		    
		    JSONObject jObj = new JSONObject(response);
		    
		    comments = jObj.getJSONArray(ConstUtilities.Node_Comments);
			    for (int i=0;i<comments.length();i++)
			    {
				    JSONObject singleObj = comments.getJSONObject(i);
				    
				    String tempContent = singleObj.getString(ConstUtilities.Node_CmtContent);
				    tempContent = tempContent.split("\n")[0];
				    tempContent = UtilFunctions.stringCleanup(tempContent);
				    _cmt.Comment = Html.fromHtml(Html.fromHtml((String) tempContent).toString());
				    String CommentType = singleObj.getString(ConstUtilities.Node_CmtType);
				   
				    JSONObject authorObj  = singleObj.getJSONObject(ConstUtilities.Node_Author);
				   _cmt.Commentor = Html.fromHtml(authorObj.getString(ConstUtilities.Node_CmtName).toString());
				   _cmt.AvatarUrl = authorObj.getString(ConstUtilities.Node_CmtAvatarUrl).toString();
				   _cmt.imgBitmap = BitmapFactory.decodeStream(new java.net.URL(_cmt.AvatarUrl).openStream()); 
				   JSONObject postObj  = singleObj.getJSONObject(ConstUtilities.Node_CmtPosts);
				   _cmt.PostID = Integer.parseInt(postObj.getString(ConstUtilities.Node_PostID));
				  
				    HashMap<String,Object> comment = new HashMap<String,Object>();
				    comment.put(ConstUtilities.Node_CmtContent, _cmt.Comment);
				    comment.put(ConstUtilities.Node_CmtName, _cmt.Commentor);
				    comment.put(ConstUtilities.Node_CmtAvatarUrl, _cmt.AvatarUrl);
				    comment.put(ConstUtilities.Node_AvatarBitmap, _cmt.imgBitmap);
				    comment.put(ConstUtilities.Node_PostID, _cmt.PostID);
				   
				    if(!(CommentType.equalsIgnoreCase("pingback")))
				    {
				    commentList.add(comment);
				    }
			    
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
			list = getListView();
	        adapter=new IMCCmtAdapter(getActivity(), commentList);
	        list.setAdapter(adapter);
			        
			    }
			 
			

		}

}

