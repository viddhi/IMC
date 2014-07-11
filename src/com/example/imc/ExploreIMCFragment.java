package com.example.imc;

import java.util.ArrayList;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;



public class ExploreIMCFragment extends Fragment {
	JSONArray categories = null;
	ImageAdapterForExplore adapter;
	IMCCategory _category = new IMCCategory();
	GridView gridView ;
	
	@Override
	   public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {	
		ConstUtilities.categoriesLists =  new ArrayList<HashMap<String,Object>>();
		    View v = inflater.inflate(R.layout.fragment_exploreimc, container, false);
		    gridView = (GridView) v.findViewById(R.id.grid_view);
		    

	        gridView.setOnItemClickListener(new OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1,int position, long arg3) {
	            	HashMap<String, Object> c = new HashMap<String, Object>();
	            	c = ConstUtilities.categoriesLists.get(position);
	                Intent i = new Intent(getActivity(), CategoriesPostList.class);
	                // passing array index
	                i.putExtra("CategoryName", c.get(ConstUtilities.Node_CategoryName).toString());
	                i.putExtra("Slug", c.get("slug").toString());
	                startActivity(i);
	            }
	        });
		    new CallAPI().execute();
		   /* for(int i=0; i<ConstUtilities.categoriesLists.size(); i++) 
	        {
		    	 TableRow row = new TableRow(getActivity());
		    	 int j=0;
		    	if(j <=2 )
		    	{
		    		HashMap<String, Object> category = new HashMap<String, Object>();
		            category = ConstUtilities.categoriesLists.get(i);
		     
		            // Setting all values in listview
		           // int PostID = Integer.parseInt(category.get(ConstUtilities.Node_CategoryID).toString());
		            String CategoryName = category.get(ConstUtilities.Node_CategoryName).toString();
		            String PostCount = category.get(ConstUtilities.Node_PostCount).toString();
		           
		            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
		            Display display = wm.getDefaultDisplay();
		            Point size = new Point();
		            display.getSize(size);
		           int width = (size.x / 4) + 30;
		           
		           TextView tv = new TextView(getActivity());
		           tv.setText(CategoryName + "\n" + PostCount + " " + "Posts");
		           tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		           tv.setTextColor(Color.WHITE);
		           
		           Random rand = new Random();
		           int randNum = rand.nextInt(4);
		           if(randNum == 1)
		           {
		           	tv.setBackgroundResource(R.drawable.lightorange);
		           }
		           else if(randNum == 2)
		           {
		           	tv.setBackgroundResource(R.drawable.darkgreen);
		           }
		           else if(randNum ==3)
		           {
		           	tv.setBackgroundResource(R.drawable.lightgreen);
		           }
		           else if(randNum == 4)
		           {
		           	tv.setBackgroundResource(R.drawable.lightorange);
		           }
		           else 
		           {
		           	tv.setBackgroundResource(R.drawable.darkorange);
		           	
		           }
		           int MaxHeight = 450;
		           int Height = 150 + Integer.parseInt(PostCount);
		           if(Height >= MaxHeight)
		           {
		           	Height = MaxHeight;
		           }
		         // tv.setHeight(Height);
		          //tv.setWidth(200);
		          
		           Typeface face=Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Light.ttf"); 
		           tv.setTypeface(face); 
	            TableRow.LayoutParams params = 
	                 (TableRow.LayoutParams)tv.getLayoutParams();
	          
	            params.width = TableRow.LayoutParams.MATCH_PARENT;
	            //Set width as needed (Important: this and the
	            //.height below is for layout of "text" inside 
	            //the TextView, not for layout of TextView' by its
	            //parent)
	            params.height = Height;
	            tv.setPadding(2, 2, 2, 2); 
	            //Skip padding (space around text) above if not
	            //needed
	            tv.setLayoutParams(params); // causes layout update. 
	            //Skip above if no special setting is needed
	                  
	        }
		    	else
		    	{
		    	 gridView.addView(row, 
			                new TableLayout.LayoutParams
			                (LayoutParams.WRAP_CONTENT, 
			                LayoutParams.WRAP_CONTENT));
		    	 j=0;
		    	}
	        }//...Here our row is all complete with 10 TextViews
	        
	        //Next add the new row to the table
*/	       
		    return v;
	    }
	
	private class CallAPI extends AsyncTask<Void, Void, Void> {
		ProgressDialog Asycdialog = new ProgressDialog(getActivity());
		 @Override
	     protected void onPreExecute() {

	         super.onPreExecute();
	         Asycdialog.setMessage("Loading...");
	         Asycdialog.show();
	     }
	@Override
	protected Void doInBackground(Void...arg0) {
		try
		{
			 //postLists = ServerUtility.returnIMCPosts();
	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    HttpEntity httpEntity = null;
	    HttpResponse httpResponse = null;
	    HttpGet httpGet = new HttpGet(ConstUtilities.getCategoriesDetail);
	    
	    httpResponse = httpClient.execute(httpGet);
	    httpEntity = httpResponse.getEntity();
	    String response = EntityUtils.toString(httpEntity);
	    
	    JSONObject jObj = new JSONObject(response);
	    
	    categories = jObj.getJSONArray("categories");
	    for (int i=0;i<categories.length();i++)
	    {
	    JSONObject singleObj = categories.getJSONObject(i);
	    
	    _category.CategoryID = Integer.parseInt(singleObj.getString(ConstUtilities.Node_CategoryID));
	    _category.CategoryName = singleObj.getString(ConstUtilities.Node_CategoryName);
	    _category.PostCount = singleObj.getString(ConstUtilities.Node_PostCount);
	    _category.Slug = singleObj.getString("slug");
	   
	    HashMap<String,Object> category = new HashMap<String,Object>();
	    category.put(ConstUtilities.Node_CategoryID,  _category.CategoryID);
	    category.put(ConstUtilities.Node_CategoryName, _category.CategoryName);
	    category.put(ConstUtilities.Node_PostCount, _category.PostCount);
	    category.put("slug", _category.Slug);
	
	   
	    ConstUtilities.categoriesLists.add(category);
	    }
	   
	    
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			
		}
	    return null;
	}
	    @Override
	    protected void onPostExecute(Void result) {
	    	super.onPostExecute(result);
	        adapter=new ImageAdapterForExplore(getActivity(), ConstUtilities.categoriesLists);
	       gridView.setAdapter(adapter);
	       gridView.setBackgroundColor(Color.WHITE);
	        super.onPostExecute(result);
	        Asycdialog.dismiss();
	    	

	    }


	    } // end CallAPI 


}