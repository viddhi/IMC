package com.example.imc;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;


public class AboutIMCFragment extends Fragment {
	
	@Override
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
			/*String Html = "<html><body><table border='0'><tr><td colspan='5' align='center' style='color:#FF6600;font-size:x-large;'>Stay in touch!</td></tr>"+
							"<tr><td><a href=''><img src='email.png' alt='Email Icon' /></a></td>" +
							"<td><a href=''><img src='facebook.png' alt='Facebook' /></a></td>" +
							"<td><a href=''><img src='twitter.png' alt='twitter' /></a></td>"+
							"<td><a href=''><img src='linkedin.png' alt='linkedin' /></a></td>"+
							"<td><a href=''><img src=pinterest.png' alt='pinterest' /> </a></td></tr><tr><td colspan='5'>"+
							"The Indian Moms Connect community was created to provide an avenue to mothers to share experiences" +
							"and stories on topics related to parenting, motherhood and children."+
							"<br>The mission of Indian Moms Connect <br> is to :-"+
							"<ol><li style='list-style-type:square'>Provide support to mothers across the globe through discussions and information on our website.</li>"+
							"<li style='list-style-type:square'>Share experiences and stories on topics related to parenting, motherhood and children in these modern times.</li>"+
							"<li style='list-style-type:square'>Provide a sense of community and a support system to mothers raising children in various age groups.</li>"+
							"<li style='list-style-type:square'>Provide a platform for moms to connect with each other and begin conversations on motherhood.</li>"+
							"<li style='list-style-type:square'>Share ideas and interesting parenting resources.</li>"+
							"</ol></td></tr></table></body></html>";	*/			
		    View v = inflater.inflate(R.layout.frament_aboutimc, container, false);
		    WebView wv = (WebView) v.findViewById(R.id.aboutIMCView);
		   /* wv.loadData(Html, "text/html", null);
		    wv.loadUrl("file:///android_asset/email.png");
		    wv.loadUrl("file:///android_asset/facebook.png");
		    wv.loadUrl("file:///android_asset/twitter.png");
		    wv.loadUrl("file:///android_asset/pinterest.png");*/
		    wv.loadUrl("file:///android_asset/aboutimc.html");
		    return v;
	    }

}
