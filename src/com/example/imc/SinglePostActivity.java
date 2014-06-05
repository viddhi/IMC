package com.example.imc;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.webkit.WebView;


@SuppressLint("DefaultLocale")
public class SinglePostActivity extends Activity {

	@SuppressLint("DefaultLocale")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        
       String Html = "<html><head><meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>"+
    		   		"</head><body><h2 style='color:#66CC33'>"+ CleanTitle + "</h2><div style='float:left;color:#CCCCCC;'><i>by:" + Author + 
    		   		"</i></div><div style='float:left;margin-left:80;color:#CCCCCC;'><i>" + DatePosted + "</i></div><br><div style='margin: 0 auto;'><a href='" + Image + "'> "+
    		   		"<img src='" + Image + "' align='center' size='medium' width='250' height='250'/></a></div><p>" + CleanContent + 
    		   		"</p></body></html>";
    		   		
        WebView myWebView = (WebView) findViewById(R.id.SinglePostView);
        myWebView.loadData(Html, "text/html", null);
       
    }
}
