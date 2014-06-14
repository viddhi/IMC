package com.example.imc;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("DefaultLocale")
public class SinglePostActivity extends Activity {
	int Position =0;
	WebView myWebView;
	String PageUrl = null;
	String PostID = null;
	boolean firstTime = true;
	 String ZillaLikeCnt = null;
	@SuppressLint("DefaultLocale")
	@Override
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_singlepost);
        myWebView = (WebView) findViewById(R.id.SinglePostView);
        
        if(firstTime)
        {
        Intent in = getIntent();
        Position = Integer.parseInt(in.getStringExtra(ConstUtilities.Position));
        }
       
        ImageView imgView1=(ImageView)findViewById(R.id.btnPrevious);
        imgView1.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		firstTime = false;
        		Position = Position - 1;
        		if(Position == -1)
        		{
        			Position = ConstUtilities.postLists.size()-1;
        		}
        		String Html = LoadWebView(Position);
        		
        		myWebView.loadDataWithBaseURL(null, Html, "text/html", "UTF-8", null);
                    }
        });
        ImageView imgView2=(ImageView)findViewById(R.id.btnNext);
        imgView2.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		firstTime = false;
        		Position = Position + 1;
        		if(Position == ConstUtilities.postLists.size())
        		{
        			Position = 0;
        		}
        		String Html = LoadWebView(Position);
        		
        		myWebView.loadDataWithBaseURL(null, Html, "text/html", "UTF-8", null);
        		
                    }
        });
        ImageView imgView3 = (ImageView)findViewById(R.id.btnshare);
        imgView3.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		final Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                
				intent.putExtra(Intent.EXTRA_TEXT, PageUrl);

                try {
                  startActivity(Intent.createChooser(intent, "Select an action"));
                } catch (android.content.ActivityNotFoundException ex) {
                  // (handle error)
                }
                    }
        });
        
        ImageView imgView4 = (ImageView)findViewById(R.id.btnLike);
        imgView4.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		boolean status = ServerUtility.setZillaLike(PostID);
        		if(status)
        		{
        			int newCount = Integer.parseInt(ZillaLikeCnt) + 1;
        			TextView zillalike= (TextView) findViewById(R.id.txtLikeCnt);
         	        zillalike.setText(String.valueOf(newCount));
        		}
        		else
        		{
        			Toast.makeText(getApplicationContext(), "Error Occured, Please try again", Toast.LENGTH_LONG).show();
        		}
                    }
        });
       
       
        String Html = LoadWebView(Position);
      
        myWebView.loadDataWithBaseURL(null, Html, "text/html", "UTF-8", null);

    }
	public void GoBack(View view) {
		WebView myWebView = (WebView) findViewById(R.id.SinglePostView);
		 if(myWebView.canGoBack()){
			 myWebView.goBack();
         }else{
             finish();
	 }
	}
	

	
	public String LoadWebView(int position)
	{
		String Image = null,DatePosted = null,Author = null,CleanTitle=null,CleanContent=null;
   		Spanned Content = null;
 		   	try
 		   	{
 		   	  HashMap<String, Object> post = new HashMap<String, Object>();
 		      post = ConstUtilities.postLists.get(position);
 		   	   
 		   	    Spanned tempTitle = (Spanned) post.get(ConstUtilities.Node_Title);
 		   	   // tempTitle = UtilFunctions.stringCleanup(tempTitle);
 		   	   // Title = Html.fromHtml(Html.fromHtml((String) tempTitle).toString());
 		   	    CleanTitle = Html.toHtml(tempTitle);
 		   	    Image = post.get(ConstUtilities.Node_Image).toString();
 		   	    DatePosted = (String) post.get(ConstUtilities.Node_Date);
 		   	    DatePosted = DatePosted.substring(0,10);
 		   	    String tempContent = (String) post.get(ConstUtilities.Node_Content);
 		   	    Content = Html.fromHtml(tempContent.toString());
 		   	    CleanContent = Html.toHtml(Content);
 		   	    Author = post.get(ConstUtilities.Node_Author).toString();
 		   	    PostID = post.get(ConstUtilities.Node_ID).toString();
 		   	   
 		   	    PageUrl = post.get(ConstUtilities.Node_PostUrl).toString();
 		   	    ZillaLikeCnt =  post.get(ConstUtilities.Zilla_Like).toString();
 		   	TextView zillalike= (TextView) findViewById(R.id.txtLikeCnt);
 	        zillalike.setText(ZillaLikeCnt);

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
    		   		
       return Html;
        
        
        
        
	}
}
