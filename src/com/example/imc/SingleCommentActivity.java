package com.example.imc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint("JavascriptInterface")
public class SingleCommentActivity extends Activity {

	@SuppressLint({ "DefaultLocale", "SetJavaScriptEnabled" })
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_singlecomment);
        Intent in = getIntent();
       
        Spanned Commentor = Html.fromHtml(in.getStringExtra(ConstUtilities.Node_CmtName));
        String CleanTitle = Html.toHtml(Commentor);
        
        String PostID = in.getStringExtra(ConstUtilities.Node_PostID);
        
        
        Spanned Content = Html.fromHtml(in.getStringExtra(ConstUtilities.Node_CmtContent));
        String CleanContent = Html.toHtml(Content);
        
        String Image = in.getStringExtra(ConstUtilities.Node_CmtAvatarUrl);
        //Android.showCorrespondingPost(ID);
        
       String Html = "<html style='display:table;margin: auto;'><head><meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>"
    		   		+"</head><body style='display: table-cell; vertical-align: middle;padding-left:10px;'>"
    		   		+ "<h2 style='color:#66CC33;text-align:left'>"+ CleanTitle + "</h2>"
    		   		+ "<div style='margin: 0 auto;float:left;'>"
    		   		+ "<img src='" + Image + "' align='center' style='margin: 0 auto;' size='medium' width='75' height='75'/></div>"
    		   		+ "<div style='float:left'><input type='button' value='Go To Post' style='text-decoration:none;color:#FF6600;margin-left:150px;' onClick='showPost('"+  PostID + "') /></div><br>"
    		   		+ "<p>" + CleanContent + "</p>"
    		   		+ "<script type='text/javascript'>function showPost(ID) { alert('Hi');}</script></body></html>";
    		   		
        WebView myWebView = (WebView) findViewById(R.id.SingleCommentView);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.addJavascriptInterface(new JavaScriptInterface(this), "Android");
        myWebView.loadData(Html, "text/html", null);
       
    }
	public void GoBack(View view) {
		WebView myWebView = (WebView) findViewById(R.id.SingleCommentView);
		 if(myWebView.canGoBack()){
			 myWebView.goBack();
         }else{
             finish();
	 }
	}
		
}
