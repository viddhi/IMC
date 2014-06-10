package com.example.imc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;


@SuppressLint("DefaultLocale")
public class SinglePostActivity extends Activity {

	@SuppressLint("DefaultLocale")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_singlepost);
        Intent in = getIntent();
       
        Spanned Title = Html.fromHtml(in.getStringExtra(ConstUtilities.Node_Title));
        String CleanTitle = Html.toHtml(Title);
        
        String DatePosted = in.getStringExtra(ConstUtilities.Node_Date);
        
        Spanned Content = Html.fromHtml(in.getStringExtra(ConstUtilities.Node_Content));
        String CleanContent = Html.toHtml(Content);
        
        String Image = in.getStringExtra(ConstUtilities.Node_Image);
        
        String Author = in.getStringExtra(ConstUtilities.Node_Author);      
       //Manipulating date using string as Calendar was giving me lot of issues.
        DatePosted = DatePosted.substring(0, 10);
        
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
