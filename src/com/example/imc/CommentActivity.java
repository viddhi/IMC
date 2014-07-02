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

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentActivity  extends ListActivity {
	JSONArray comments = null;
	//Array list of comments 
	ArrayList<HashMap<String,Object>> commentList;
	IMCComment _cmt = new IMCComment();
	ListView list;
	IMCCmtAdapter adapter;
	Dialog myDialog = null;
	View rootView;
	String PostID = null;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_comment);
        commentList = new ArrayList<HashMap<String,Object>>();
        list = getListView();
        Intent in = getIntent();
        PostID = in.getStringExtra("CommentPostID");
        TextView txtView1=(TextView)findViewById(R.id.shareLink);
        txtView1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		callCommentDialog();
                    }
        });
        new CallAPI().execute();
	}
	
	private void callCommentDialog() 
    {
         myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.activity_submitcmt);
        myDialog.setCancelable(true);
        myDialog.setTitle( "Share your thoughts" );
        Button submit = (Button) myDialog.findViewById(R.id.ButtonSendFeedback);

         myDialog.show();

         submit.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   EditText emailaddr = (EditText) myDialog.findViewById(R.id.EditTextEmail);
               EditText username = (EditText) myDialog.findViewById(R.id.EditTextName);
               EditText message = (EditText) myDialog.findViewById(R.id.EditTextFeedbackBody);
               boolean status = ServerUtility.postComment(PostID, username.getText().toString(), emailaddr.getText().toString(), message.getText().toString());
               if(status)
               {
            	   Toast.makeText(getApplicationContext(), "Thank you adding your thoughts! Your comment is pending and will be approved by the admin soon", Toast.LENGTH_LONG).show();
            	   myDialog.dismiss();

               }
               else
               {
            	   Toast.makeText(getApplicationContext(), "Thank you adding your thoughts! Your comment is pending and will be approved by the admin soon", Toast.LENGTH_LONG).show();
            	   myDialog.dismiss();
               }
           }
       });
       

    }

	private class CallAPI extends AsyncTask<Void, Void, Void> {
		ProgressDialog Asycdialog = new ProgressDialog(CommentActivity.this);
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
			String URL = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts/" + PostID + "/replies?pretty=1";
		    DefaultHttpClient httpClient = new DefaultHttpClient();
		    HttpEntity httpEntity = null;
		    HttpResponse httpResponse = null;
		    HttpGet httpGet = new HttpGet(URL);
		    
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
			
	        adapter=new IMCCmtAdapter(CommentActivity.this, commentList);
	        list.setAdapter(adapter);
	        Asycdialog.dismiss();
			        
			    }
			 
			

		}

}

