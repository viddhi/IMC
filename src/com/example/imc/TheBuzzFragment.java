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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;


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
		 if(commentList.size() == 0)
		 {
		new CallAPI().execute();
		 }
		return rootView;
	}
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	  
	    ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
  	
		@Override
      public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
  		String CommentorName = ((TextView) arg1.findViewById(R.id.Commentor)).getText().toString();
  		String Content = ((TextView) arg1.findViewById(R.id.FullContent)).getText().toString();
  		String AvatarURL = ((TextView) arg1.findViewById(R.id.AvatarUrl)).getText().toString();
  		String PostID = ((TextView) arg1.findViewById(R.id.PostID)).getText().toString();
  		
  		Intent in = new Intent(getActivity(),SingleCommentActivity.class);
  		in.putExtra(ConstUtilities.Node_CmtName, CommentorName);
  		in.putExtra(ConstUtilities.Node_CmtContent, Content);
  		in.putExtra(ConstUtilities.Node_CmtAvatarUrl, AvatarURL);
  		in.putExtra(ConstUtilities.Node_PostID, PostID);
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
				    //tempContent = tempContent.split("\n")[0];
				    tempContent = UtilFunctions.stringCleanup(tempContent);
				    _cmt.Comment = Html.fromHtml(Html.fromHtml((String) tempContent).toString());
				    String CommentType = singleObj.getString(ConstUtilities.Node_CmtType);
				   
				    JSONObject authorObj  = singleObj.getJSONObject(ConstUtilities.Node_Author);
				   _cmt.Commentor = Html.fromHtml(authorObj.getString(ConstUtilities.Node_CmtName).toString());
				   _cmt.AvatarUrl = authorObj.getString(ConstUtilities.Node_CmtAvatarUrl).toString();
				   JSONObject postObj  = singleObj.getJSONObject(ConstUtilities.Node_CmtPosts);
				   _cmt.PostID = Integer.parseInt(postObj.getString(ConstUtilities.Node_PostID));
				  
				    HashMap<String,Object> comment = new HashMap<String,Object>();
				    comment.put(ConstUtilities.Node_CmtContent, _cmt.Comment);
				    comment.put(ConstUtilities.Node_CmtName, _cmt.Commentor);
				    comment.put(ConstUtilities.Node_CmtAvatarUrl, _cmt.AvatarUrl);
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
	        Asycdialog.dismiss();
			        
			    }
			 
			

		}

}

