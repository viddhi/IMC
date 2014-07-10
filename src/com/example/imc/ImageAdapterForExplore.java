package com.example.imc;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class ImageAdapterForExplore extends BaseAdapter {

	    private ArrayList<HashMap<String,Object>> data;
	    private Context mContext;
 
    // Constructor
    public ImageAdapterForExplore(Activity a, ArrayList<HashMap<String,Object>> d) {
      
        mContext = a;
        data=d;
      
        
    }
 
    public int getCount() {
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	
         HashMap<String, Object> category = new HashMap<String, Object>();
         category = data.get(position);
  
         // Setting all values in listview
        // int PostID = Integer.parseInt(category.get(ConstUtilities.Node_CategoryID).toString());
         String CategoryName = category.get(ConstUtilities.Node_CategoryName).toString();
         String PostCount = category.get(ConstUtilities.Node_PostCount).toString();
        
         WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
         Display display = wm.getDefaultDisplay();
         Point size = new Point();
         display.getSize(size);
         int width = (size.x / 3) - 30;
        // int height = size.y;
        TextView tv = new TextView(mContext);
        tv.setText(CategoryName + "\n" + PostCount + " " + "Posts");
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tv.setTextColor(Color.WHITE);
        tv.setWidth(width);
        if(position % 3 == 0)
        {
        	tv.setBackgroundResource(R.drawable.darkgreen);
        }
        else if(position % 4 ==0)
        {
        	tv.setBackgroundResource(R.drawable.lightgreen);
        }
        else if(position % 5 ==0)
        {
        	tv.setBackgroundResource(R.drawable.lightorange);
        }
        else if(position % 6 ==0)
        {
        	tv.setBackgroundResource(R.drawable.darkorange);
        }
        else 
        {
        	tv.setBackgroundResource(R.drawable.darkorange);
        }
        int Height = 100 + Integer.parseInt(PostCount);
        if(Height > 200)
        {
        	Height = 200;
        }
        tv.setHeight(Height);
        Typeface face=Typeface.createFromAsset(mContext.getAssets(), "Roboto-Light.ttf"); 
        tv.setTypeface(face); 
       
        return tv;
     
        
    }
 
}
