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

import com.imc.mobile.android.R;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

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
	String PostTitle = null;
	 public Typeface face;
	 SharedPreferences prefs =null;
	 String CmtName = "com.imc.mobile.android.CmtName";
	 String CmtEmail = "com.imc.mobile.android.CmtEmail";
	 String storedName = "";
	 String storedEmail = "";
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_comment);
        commentList = new ArrayList<HashMap<String,Object>>();
        list = getListView();
        face=Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"); 
        Intent in = getIntent();
        PostID = in.getStringExtra("CommentPostID");
        PostTitle = in.getStringExtra("CommentPostTitle");
         prefs = getSharedPreferences("com.imc.mobile.android", Context.MODE_PRIVATE);
		
        
        list.setOnItemClickListener(new OnItemClickListener() {
  	
		@Override
      public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
  		String CommentorName = ((TextView) arg1.findViewById(R.id.Commentor)).getText().toString();
  		String Content = ((TextView) arg1.findViewById(R.id.FullContent)).getText().toString();
  		String AvatarURL = ((TextView) arg1.findViewById(R.id.AvatarUrl)).getText().toString();
  		String PostID = ((TextView) arg1.findViewById(R.id.PostID)).getText().toString();
  		
  		Intent in = new Intent(getApplicationContext(),SingleCommentActivity.class);
  		in.putExtra(ConstUtilities.Node_CmtName, CommentorName);
  		in.putExtra(ConstUtilities.Node_CmtContent, Content);
  		in.putExtra(ConstUtilities.Node_CmtAvatarUrl, AvatarURL);
  		in.putExtra(ConstUtilities.Node_PostID, PostID);
  		startActivity(in);
  		
  	}
  	
  });
        
        //Share link on click
        TextView txtView1=(TextView)findViewById(R.id.shareLink);
        txtView1.setTypeface(face);
        txtView1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		callCommentDialog();
                    }
        });
        //Buzz on click
        face=Typeface.createFromAsset(getAssets(), "Roboto-Black.ttf"); 
        TextView txtView2=(TextView)findViewById(R.id.Header);
        txtView2.setTypeface(face);
        txtView2.setText(Html.fromHtml(PostTitle));
        txtView2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        txtView2.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		finish();
        	}
        });
        
        new CallAPI().execute();
	}
	
	private void callCommentDialog() 
    {
		 
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.activity_submitcmt);
        myDialog.setCancelable(true);
        ((TextView)myDialog.findViewById(R.id.EditTextEmail)).setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"));
        ((TextView)myDialog.findViewById(R.id.EditTextName)).setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"));
        ((TextView)myDialog.findViewById(R.id.EditTextFeedbackBody)).setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"));
        myDialog.setTitle( "Share your thoughts" );
        ((TextView)myDialog.findViewById(android.R.id.title)).setTypeface(Typeface.createFromAsset(getAssets(),"Roboto-Black.ttf"));
        //Checking if their name is in share preference if so set else leave it empty
        EditText emailaddr = (EditText) myDialog.findViewById(R.id.EditTextEmail);
        EditText username = (EditText) myDialog.findViewById(R.id.EditTextName);
         storedName = prefs.getString(CmtName, null);
         storedEmail = prefs.getString(CmtEmail, null);
	        if(storedName != null)
	        {
	        username.setText(storedName);
	        }
	        if(storedEmail != null)
	        {
	        	emailaddr.setText(storedEmail);
	        }
       
        
        
        Button submit = (Button) myDialog.findViewById(R.id.ButtonSendFeedback);
        submit.setTypeface(Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"));
         myDialog.show();

         submit.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
        	   EditText emailaddr = (EditText) myDialog.findViewById(R.id.EditTextEmail);
               EditText username = (EditText) myDialog.findViewById(R.id.EditTextName);
               EditText message = (EditText) myDialog.findViewById(R.id.EditTextFeedbackBody);
               prefs.edit().putString(CmtEmail, emailaddr.getText().toString()).apply();
               prefs.edit().putString(CmtName, username.getText().toString()).apply();
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

