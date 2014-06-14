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

import android.os.StrictMode;
import android.text.Html;

public class ServerUtility {
	public static int returnPostCount(String PrevCheckedDate)
	{
		int NumOfPostsFound =0;
		try
		{
			//Strict mode
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		    
			 DefaultHttpClient httpClient = new DefaultHttpClient();
	         HttpEntity httpEntity = null;
	         HttpResponse httpResponse = null;
	         String Url = String.format(ConstUtilities.getNumberOfPosts, PrevCheckedDate.substring(0, 10));
	         HttpGet httpGet = new HttpGet(Url);
         
	         httpResponse = httpClient.execute(httpGet);
	         httpEntity = httpResponse.getEntity();
	         String response = EntityUtils.toString(httpEntity);
         
	         JSONObject jObj = new JSONObject(response);
	         NumOfPostsFound = jObj.getInt(ConstUtilities.Node_Found);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
         
        
		return NumOfPostsFound;
	}
	public static boolean setZillaLike(String PostID)
	{
		String URL = "http://www.indianmomsconnect.com/?json=set_zilla_likes&id=" + PostID;
		try
		{
			//Strict mode
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		    StrictMode.setThreadPolicy(policy);
		    
			 DefaultHttpClient httpClient = new DefaultHttpClient();
	         HttpEntity httpEntity = null;
	         HttpResponse httpResponse = null;
	         HttpGet httpGet = new HttpGet(URL);
	         httpResponse = httpClient.execute(httpGet);
	         httpEntity = httpResponse.getEntity();
	         String response = EntityUtils.toString(httpEntity);
         
	         JSONObject jObj = new JSONObject(response);
	         String Status = jObj.getString("status");
	         if(Status.equalsIgnoreCase("ok"))
	         {
	        	 return true;
	         }
	         else
	         {
	        	 return false;
	         }
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
			
		}

	}
	
	@SuppressWarnings("null")
	public static ArrayList<HashMap<String,Object>> returnIMCPosts()
	{
		JSONArray posts = null;
		//Array list of post lists
		ArrayList<HashMap<String,Object>> postLists = null;
		IMCPost _post = new IMCPost();
		try
		{
			 
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
	   // tempTitle = URLDecoder.decode(tempTitle,"UTF-8");
	    //tempTitle = tempTitle.replaceAll( "[\\u202C\\u202A]", "" );
	    _post.Title = Html.fromHtml(Html.fromHtml((String) tempTitle).toString());
	    String excerptData = singleObj.getString(ConstUtilities.Node_Excerpt);
	    excerptData = UtilFunctions.stringCleanup(excerptData);
	   /* excerptData = URLDecoder.decode(excerptData, "UTF-8");
	    excerptData = excerptData.replaceAll( "[\\u202C\\u202A]", "" );*/
	    _post.Excerpt = Html.fromHtml(Html.fromHtml((String) excerptData).toString());
	    _post.Image = singleObj.getString(ConstUtilities.Node_Image).toString();
	    _post.LikeCount = "Likes: " + singleObj.getString(ConstUtilities.Node_LikeCnt);
	    _post.PostID = singleObj.getString(ConstUtilities.Node_ID);
	    _post.CommentCount = "Comments: " + singleObj.getString(ConstUtilities.Node_CommentCnt);
	    _post.dateposted = singleObj.getString(ConstUtilities.Node_Date);
	   // _post.Content = singleObj.getString(ConstUtilities.Node_Content);
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
		return postLists;
	}

}
