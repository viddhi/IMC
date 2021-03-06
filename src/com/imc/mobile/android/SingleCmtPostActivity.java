package com.imc.mobile.android;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.imc.mobile.android.R;
import com.imc.mobile.android.GlobalState.TrackerName;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;

public class SingleCmtPostActivity extends Activity {
	public Typeface face;
	@SuppressLint("DefaultLocale")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
     // Get tracker.
        Tracker t = ((GlobalState)this.getApplication()).getTracker(TrackerName.APP_TRACKER);
        // Set screen name.Where path is a String representing the screen name.
        t.setScreenName("/SinglePostViewFromComment");
        t.send(new HitBuilders.AppViewBuilder().build());
        setContentView(R.layout.activity_singlecmtpost);
        Intent in = getIntent();
       String PostID = in.getStringExtra("CPostID");
       String Image = null,DatePosted = null,Author = null,CleanTitle=null,CleanContent=null;
  		Spanned Title = null;
  		String HtmlString="";
  		 face=Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"); 
         Button btnback=(Button)findViewById(R.id.btnPrev);
         btnback.setTypeface(face);
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
		   	    DatePosted = DatePosted.substring(0,10);
		   	    CleanContent = jObj.getString(ConstUtilities.Node_Content);
		   	   
		   	    JSONObject authorObj  = jObj.getJSONObject(ConstUtilities.Node_Author);
		   	    Author = authorObj.getString("name").toString();

   		}
	   		catch(Exception ex)
	   		{
	   			ex.printStackTrace();
	   			
	   		}
   
       /*String Html = "<html style='display:table;margin: auto;'><head><meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>"+
    		   		"</head><body style='display: table-cell; vertical-align: middle;padding-left:10px;'>"
    		   		+ "<h2 style='color:#66CC33;text-align:center'>"+ CleanTitle + "</h2><div style='margin: 0 auto;'>"
    		   		+ "<div style='float:left;color:#CCCCCC;'><i>by:" + Author + 
    		   		"</i></div>"
    		   		+ "<div style='float:left;margin-left:90;color:#CCCCCC;'><i>" + DatePosted + "</i></div>"
    		   		+ "<div align='center' style='margin: 0 auto;'><a href='" + Image + "'> "+
    		   		"<img src='" + Image + "' align='center' style='margin: 0 auto;' size='medium' width='250' height='250'/></a></div></div>"
    		   		+ "<p>" + CleanContent + 
    		   		"</p></body></html>";*/
			if(Image.isEmpty())
 		   	{
 		   	HtmlString = "<html><head><meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>"+
     		   		"</head><body>"
     		   		+ "<h2 style='color:#66CC33;text-align:center'>"+ CleanTitle + "</h2><div style='margin: 0 auto;'>"
     		   		+ "<div style='float:left;color:#CCCCCC;'><i>by:" + Author + 
     		   		"</i></div>"
     		   		+ "<div style='float:right;color:#CCCCCC;'><i>" + DatePosted + "</i></div><br>"
     		   		+"</div>"
     		   		+ "<p>" + CleanContent + 
     		   		"</p></body></html>";
  		   	
 		   	}
 		   	else
 		   	{
 		   	HtmlString = "<html><head> <meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'><meta charset='UTF-8'>"+
    		   		"</head><body>"
    		   		+ "<h2 style='color:#66CC33;text-align:center'>"+ CleanTitle + "</h2><div style='margin: 0 auto;'>"
    		   		+ "<div style='float:left;color:#CCCCCC;'><i>by:" + Author + 
    		   		"</i></div>"
    		   		+ "<div style='float:right;color:#CCCCCC;'><i>" + DatePosted + "</i></div><br>"
    		   		+ "<div align='center'><a href='" + Image + "'> "+
    		   		"<img src='" + Image + "' align='center' style='margin: 0 auto;' size='medium' width='250' height='250'/></a></div></div>"
    		   		+ "<p>" + CleanContent + 
    		   		"</p></body></html>";
 		   	}
    		   		
        WebView myWebView = (WebView) findViewById(R.id.SinglePostView);
        myWebView.loadDataWithBaseURL(null, HtmlString, "text/html", "UTF-8", null);
       
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

