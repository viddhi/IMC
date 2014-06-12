package com.example.imc;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;

public class SingleCmtPostActivity extends Activity {

	@SuppressLint("DefaultLocale")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_singlecmtpost);
        Intent in = getIntent();
       String PostID = in.getStringExtra("CPostID");
       String Image = null,DatePosted = null,Author = null,CleanTitle=null,CleanContent=null;
  		Spanned Title = null,Content = null;
       String URL = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts/" + PostID + "?pretty=1";
		   	try
		   	{
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
		   	    
		   	    String tempTitle = jObj.getString(ConstUtilities.Node_Title);
		   	    tempTitle = UtilFunctions.stringCleanup(tempTitle);
		   	    Title = Html.fromHtml(Html.fromHtml((String) tempTitle).toString());
		   	    CleanTitle = Html.toHtml(Title);
		   	    Image = jObj.getString(ConstUtilities.Node_Image).toString();
		   	    DatePosted = jObj.getString(ConstUtilities.Node_Date);
		   	    String tempContent = jObj.getString(ConstUtilities.Node_Content);
		   	    tempContent = UtilFunctions.stringCleanup(tempContent);
		   	    Content = Html.fromHtml(Html.fromHtml((String) tempContent).toString());
		   	    CleanContent = Html.toHtml(Content);
		   	    JSONObject authorObj  = jObj.getJSONObject(ConstUtilities.Node_Author);
		   	    Author = authorObj.getString("name").toString();

   		}
	   		catch(Exception ex)
	   		{
	   			ex.printStackTrace();
	   			
	   		}
   
       String Html = "<html style='display:table;margin: auto;'><head><meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>"+
    		   		"</head><body style='display: table-cell; vertical-align: middle;padding-left:10px;'>"
    		   		+ "<h2 style='color:#66CC33;text-align:center'>"+ CleanTitle + "</h2><div style='margin: 0 auto;'>"
    		   		+ "<div style='float:left;color:#CCCCCC;'><i>by:" + Author + 
    		   		"</i></div>"
    		   		+ "<div style='float:left;margin-left:90;color:#CCCCCC;'><i>" + DatePosted + "</i></div>"
    		   		+ "<div style='margin: 0 auto;'><a href='" + Image + "'> "+
    		   		"<img src='" + Image + "' align='center' style='margin: 0 auto;' size='medium' width='250' height='250'/></a></div></div>"
    		   		+ "<p>" + CleanContent + 
    		   		"</p></body></html>";
    		   		
        WebView myWebView = (WebView) findViewById(R.id.SinglePostView);
        myWebView.loadData(Html, "text/html", null);
       
    }
	public void GoBack(View view) {
		WebView myWebView = (WebView) findViewById(R.id.SinglePostView);
		 if(myWebView.canGoBack()){
			 myWebView.goBack();
         }else{
             finish();
	 }
	}
}

