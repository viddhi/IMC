package com.example.imc;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.Spanned;


public class JavaScriptInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    JavaScriptInterface(Context c) {
        mContext = c;
    }


    /** Show a toast from the web page */
                
    public void showCorrespondingPost(String ID) {
    	String URL = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts/" + ID + "?pretty=1";
    	try
    	{
    		JSONArray posts = null;
    		
    		DefaultHttpClient httpClient = new DefaultHttpClient();
    	    HttpEntity httpEntity = null;
    	    HttpResponse httpResponse = null;
    	    HttpGet httpGet = new HttpGet(URL);
    	    
    	    httpResponse = httpClient.execute(httpGet);
    	    httpEntity = httpResponse.getEntity();
    	    String response = EntityUtils.toString(httpEntity);
    	    
    	    JSONObject jObj = new JSONObject(response);
    	    
    	    posts = jObj.getJSONArray(ConstUtilities.Node_Posts);
    	    int i=0;
    	    JSONObject singleObj = posts.getJSONObject(i);
    	    String tempTitle = singleObj.getString(ConstUtilities.Node_Title);
    	    tempTitle = UtilFunctions.stringCleanup(tempTitle);
    	    Spanned Title = Html.fromHtml(Html.fromHtml((String) tempTitle).toString());
    	  
    	    String Image = singleObj.getString(ConstUtilities.Node_Image).toString();
    	    
    	    String PostID  = singleObj.getString(ConstUtilities.Node_ID);
    	   
    	    String DatePosted = singleObj.getString(ConstUtilities.Node_Date);
    	    String Content = singleObj.getString(ConstUtilities.Node_Content);
    	    JSONObject authorObj  = singleObj.getJSONObject(ConstUtilities.Node_Author);
    	    String Author = authorObj.getString("name").toString();

    	    Intent in = new Intent(mContext, IMCPost.class); 
    		in.putExtra(ConstUtilities.Node_ID, PostID);
    		in.putExtra(ConstUtilities.Node_Title, Title);
    		in.putExtra(ConstUtilities.Node_Date, DatePosted);
    		in.putExtra(ConstUtilities.Node_Content, Content);
    		in.putExtra(ConstUtilities.Node_Author, Author);
    		in.putExtra(ConstUtilities.Node_Image, Image);
            mContext.startActivity(in); 
    		}
    		
    		catch(Exception ex)
    		{
    			ex.printStackTrace();
    			
    		}
    	}
 
		


    }



