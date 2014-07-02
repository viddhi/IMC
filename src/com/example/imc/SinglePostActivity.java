package com.example.imc;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
	 String CmtCnt = null;
	 ProgressDialog progressBar;
	 private int progressBarStatus = 0;
	 private Handler progressBarHandler = new Handler();
	 boolean Status= true;
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
        		// prepare for a progress bar dialog
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("In Progress ...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.show();
                progressBar.setMax(100);
                //reset progress bar status
                progressBarStatus = 0;
                new Thread(new Runnable() {
                  public void run() {
                    while (progressBarStatus < 100) {

                      // process some tasks
                    	progressBarStatus = 50;
                    	progressBar.setProgress(progressBarStatus);
                    	Status = ServerUtility.setZillaLike(PostID);
                    	progressBarStatus =100;

                      // your computer is too fast, sleep 1 second
                      try {
                        Thread.sleep(1000);
                      } catch (InterruptedException e) {
                        e.printStackTrace();
                      }

                      // Update the progress bar
                      progressBarHandler.post(new Runnable() {
                        public void run() {
                          progressBar.setProgress(progressBarStatus);
                        }
                      });
                    }

                    // ok, file is downloaded,
                    if (progressBarStatus >= 100) {

                        // sleep 2 seconds, so that you can see the 100%
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // close the progress bar dialog
                        progressBar.dismiss();
                    }
                  }
                   }).start();

        		if(Status)
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
        
        ImageView imgView5 = (ImageView)findViewById(R.id.btnCmt);
        imgView5.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		Intent in = new Intent(getApplicationContext(),CommentActivity.class);
        		in.putExtra("CommentPostID", PostID);
          		startActivity(in);
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
 		   	    CleanContent = CleanContent.replace("1024", "250");
 		   	 CleanContent = CleanContent.replace("576", "250");
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
     		   		+ "<div style='float:left;margin-left:90;color:#CCCCCC;'><i>" + DatePosted + "</i></div><br>"
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
    		   		+ "<div style='float:left;margin-left:90;color:#CCCCCC;'><i>" + DatePosted + "</i></div><br>"
    		   		+ "<div><a href='" + Image + "'> "+
    		   		"<img src='" + Image + "' align='center' style='margin: 0 auto;' size='medium' width='250' height='250'/></a></div></div>"
    		   		+ "<p>" + CleanContent + 
    		   		"</p></body></html>";
 		   	}
    		   		
       return HtmlString;
        
        
        
        
	}
}
