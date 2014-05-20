package com.example.imc;




import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;


@SuppressLint("DefaultLocale")
public class SinglePostActivity extends Activity {
	
	// JSON node keys
	
	private static final String Node_Title = "title";
	private static final String Node_Date = "date";
	private static final String Node_Content = "content";
	private static final String Node_Image = "featured_image";
	private static final String Node_Author = "author";

	
	@SuppressLint("DefaultLocale")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlepost);
        Intent in = getIntent();
       
        String Title = in.getStringExtra(Node_Title);
        String DatePosted = in.getStringExtra(Node_Date);
        String Content = in.getStringExtra(Node_Content);
        String Image = in.getStringExtra(Node_Image);
        //Image = Image.replace('\', ' ');
        String Author = in.getStringExtra(Node_Author);
        
       //Manipulating date using string as Calendar was giving me lot of issues.
        DatePosted = DatePosted.substring(0, 10);
       //String Year = DatePosted.split("-")[0];
       //String Month = DatePosted.split("-")[1];
       //String DatePart = DatePosted.split("-")[2];
       String Html = "<html><head><meta name='viewport' content='width=device-width, initial-scale=1, maximum-scale=1'>"+
    		   		"</head><body><h2 style='color:#66CC33'>"+ Title + "</h2><div style='float:left;'>By:" + Author + 
    		   		"</div><div style='float:left;margin-left:50'>Date:" + DatePosted + "</div><br><a href='" + Image + "'> "+
    		   		"<img src=" + Image + "' align='center' size='medium' width='300' height='300'/></a><p>" + Content + 
    		   		"</p></body></html>";
    		   		
        WebView myWebView = (WebView) findViewById(R.id.SinglePostView);
        myWebView.loadData(Html, "text/html", null);
       
    }
}
