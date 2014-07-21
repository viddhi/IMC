package com.example.imc;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;




@SuppressLint({ "DefaultLocale", "SetJavaScriptEnabled" })
public class SinglePostActivity extends Activity {
	int Position =0;
	WebView myWebView;
	String PageUrl = null;
	String PostID = null;
	boolean firstTime = true;
	 String ZillaLikeCnt = null;
	 String CmtCnt = null;
	 ProgressDialog progressBar;
	 boolean Status= true;
	 public Typeface face;
	 Dialog myDialog = null;
	@SuppressLint("DefaultLocale")
	@Override
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();
        setContentView(R.layout.activity_singlepost);
        face=Typeface.createFromAsset(getAssets(), "Roboto-Light.ttf"); 
        myWebView = (WebView) findViewById(R.id.SinglePostView);
        myWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        Button btnback = (Button) findViewById(R.id.btnPrev);
        TextView tv1 = (TextView) findViewById(R.id.txtCmtCnt);
        TextView tv2 = (TextView) findViewById(R.id.txtLikeCnt);
        tv1.setTypeface(face);
        tv2.setTypeface(face);
        btnback.setTypeface(face);
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
        		myWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
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
        		myWebView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
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
        		int newCount = Integer.parseInt(ZillaLikeCnt) + 1;
    			TextView zillalike= (TextView) findViewById(R.id.txtLikeCnt);
     	        zillalike.setText(String.valueOf(newCount));
     	        callThankYouDialog();
                Status = ServerUtility.setZillaLike(PostID);
                
                
             }
        });
        
        ImageView imgView5 = (ImageView)findViewById(R.id.btnCmt);
        imgView5.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent in = new Intent(getApplicationContext(),CommentActivity.class);
        		in.putExtra("CommentPostID", PostID);
          		startActivity(in);
                    }
        });
       
       
        String Html = LoadWebView(Position);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setDomStorageEnabled(true);
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
	
	private void callThankYouDialog() 
    {
        myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.activity_likemsg);
        myDialog.setCancelable(true);
        myDialog.setTitle( "Thank You for your interest " );
        Button cancel = (Button) myDialog.findViewById(R.id.ButtonCancel);
        Button share = (Button) myDialog.findViewById(R.id.ButtonShare);
        

         myDialog.show();

         cancel.setOnClickListener(new OnClickListener()
        {

           @Override
           public void onClick(View v)
           {
 
            	   myDialog.dismiss();
           }
               
       });
         share.setOnClickListener(new OnClickListener()
         {

            @Override
            public void onClick(View v)
            {
  
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
       

    }
	
	public String LoadWebView(int position)
	{
		String Image = null,DatePosted = null,Author = null,CleanTitle=null,CleanContent=null;
   		String HtmlString=null;
 		   	try
 		   	{
 		   	  HashMap<String, Object> post = new HashMap<String, Object>();
 		      post = ConstUtilities.postLists.get(position);
 		   	   
 		   	    Spanned tempTitle = (Spanned) post.get(ConstUtilities.Node_Title);
 		   	    CleanTitle = Html.toHtml(tempTitle);
 		   	    Image = post.get(ConstUtilities.Node_Image).toString();
 		   	    DatePosted = (String) post.get(ConstUtilities.Node_Date);
 		   	    DatePosted = DatePosted.substring(0,10);
 		   	    CleanContent = (String) post.get(ConstUtilities.Node_Content);
 		   	    
 		   	    Author = post.get(ConstUtilities.Node_Author).toString();
 		   	    PostID = post.get(ConstUtilities.Node_ID).toString();
 		   	   
 		   	    PageUrl = post.get(ConstUtilities.Node_PostUrl).toString();
 		   	    ZillaLikeCnt =  post.get(ConstUtilities.Zilla_Like).toString();
 		   	    CmtCnt = post.get(ConstUtilities.Node_CommentCnt).toString().split(":")[1];
 		   	TextView zillalike= (TextView) findViewById(R.id.txtLikeCnt);
 	        zillalike.setText(ZillaLikeCnt);
 	        
 	        TextView cmtLike = (TextView)findViewById(R.id.txtCmtCnt);
 	        cmtLike.setText(CmtCnt);

    		}
 	   		catch(Exception ex)
 	   		{
 	   			ex.printStackTrace();
 	   			
 	   		}
        
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
    		   		"<script type='text/javascript'>"+
    		   		"function resize(image)" +
    		   		"{" +
    		   			"var differenceHeight = document.body.clientHeight - image.clientHeight;"+
    		   			"var differenceWidth  = document.body.clientWidth  - image.clientWidth;"+
    		   			"if (differenceHeight < 0) differenceHeight = differenceHeight * -1;"+
    		   			"if (differenceWidth  < 0) differenceWidth  = differenceWidth * -1;"+
    		   			"if (differenceHeight > differenceWidth)"+
    		   			"{"+
    		   			"image.style['height'] = document.body.clientHeight + 'px';"+
 		        		"}"+
 		        		"else"+
 		        		"{"+
 		        		"image.style['width'] = document.body.clientWidth + 'px';"+
 		   				"}"+
 		   				"image.style['margin'] = 0;"+
 		   				"document.body.style['margin'] = 0;"+
 		   				"}"+
 		   				"</script>" +
 		   			"</head><body>" 
    		   		+ "<h2 style='color:#66CC33;text-align:center'>"+ CleanTitle + "</h2><div style='margin: 0 auto;'>"
    		   		+ "<div style='float:left;color:#CCCCCC;'><i>by:" + Author + 
    		   		"</i></div>"
    		   		+ "<div style='float:right;color:#CCCCCC;'><i>" + DatePosted + "</i></div><br>"
    		   		+ "<div align='center'><a href='" + Image + "'> "+
    		   		"<img src='" + Image + "' align='center' style='margin: 0 auto;' size='small' width='90%' height='70%'/></a></div></div>"
    		   		+ "<p>" + CleanContent + 
    		   		"</p></body></html>";
 		   	}
    		   		
       return HtmlString;
        
        
        
        
	}
}
