package com.example.imc;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;


@SuppressLint("JavascriptInterface")
public class SingleCommentActivity extends Activity {
	String PostID = null;
	public Typeface face;
	@SuppressLint({ "DefaultLocale", "SetJavaScriptEnabled" })
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_singlecomment);
        face=Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"); 
        Button btnback=(Button)findViewById(R.id.btnPrev);
        btnback.setTypeface(face);
        Button Tbutun=(Button)findViewById(R.id.btnToPost);
        Tbutun.setTypeface(face);
        Tbutun.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		    	    
		    	    Intent in = new Intent(v.getContext(),SingleCmtPostActivity.class);
		    		in.putExtra("CPostID", PostID);
		    		in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		    		v.getContext().startActivity(in); 
                    }
        });
        Intent in = getIntent();
        Spanned Commentor = Html.fromHtml(in.getStringExtra(ConstUtilities.Node_CmtName));
        String CleanTitle = Html.toHtml(Commentor);
        PostID = in.getStringExtra(ConstUtilities.Node_PostID);
        Spanned Content = Html.fromHtml(in.getStringExtra(ConstUtilities.Node_CmtContent));
        String CleanContent = Html.toHtml(Content);
        String Image = in.getStringExtra(ConstUtilities.Node_CmtAvatarUrl);
        //Android.showCorrespondingPost(ID);
        
       String Html = "<html style='display:table;margin: auto;'><head><meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>"
    		   		+"</head><body style='display: table-cell; vertical-align: middle;padding-left:10px;'>"
    		   		+ "<h2 style='color:#66CC33;text-align:left'>"+ CleanTitle + "</h2>"
    		   		+ "<div style='margin: 0 auto;'>"
    		   		+ "<img src='" + Image + "' align='center' style='margin: 0 auto;' size='medium' width='75' height='75'/></div>"
    		   		+ "<p>" + CleanContent + "</p>"
    		   		+ "</body></html>";
    		   		
        WebView myWebView = (WebView) findViewById(R.id.SingleCommentView);
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
