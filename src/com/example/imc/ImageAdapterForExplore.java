package com.example.imc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

 
public class ImageAdapterForExplore extends BaseAdapter {

	    private ArrayList<HashMap<String,Object>> data;
	    private static LayoutInflater inflater=null;
	    private Context mContext;
 
    // Constructor
    public ImageAdapterForExplore(Activity a, ArrayList<HashMap<String,Object>> d) {
      
        mContext = a;
        data=d;
        inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
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
    	
    	  View vi=convertView;
          if(convertView==null)
              vi = inflater.inflate(R.layout.gridlist_item, null);
         HashMap<String, Object> category = new HashMap<String, Object>();
         category = data.get(position);
  
         // Setting all values in listview
        // LinearLayout llParent = (LinearLayout)vi.findViewById(R.id.llParent);
         TextView tv = (TextView)vi.findViewById(R.id.txtCategory); 
        		 //new TextView(mContext);
        		 //
         String CategoryName = category.get(ConstUtilities.Node_CategoryName).toString();
         String PostCount = category.get(ConstUtilities.Node_PostCount).toString();
        // String Slug = category.get("slug").toString();
         WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
         Display display = wm.getDefaultDisplay();
         Point size = new Point();
         display.getSize(size);
         int Width = 322;
        		 //(size.x / 4) + 30;
       
        tv.setText(CategoryName + "\n" + PostCount + " " + "Posts");
        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tv.setTextColor(Color.WHITE);
        int imgID = mContext.getResources().getIdentifier("travel","drawable", mContext.getPackageName());
        tv.setCompoundDrawablesWithIntrinsicBounds(imgID , 0, 0, 0);
        
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
        int MaxHeight = 420;
        int Height = 265 + Integer.parseInt(PostCount);
        if(Height >= MaxHeight)
        {
        	Height = MaxHeight;
        }
       tv.setHeight(Height);
       tv.setWidth(Width);
        tv.setLayoutParams(new AbsListView.LayoutParams(Width,Height));
        Typeface face=Typeface.createFromAsset(mContext.getAssets(), "Roboto-Light.ttf"); 
        tv.setTypeface(face); 
      //  llParent.addView(tv);
      // tv.setLayoutParams(new GridView.LayoutParams(Width, Height));
        return vi;
     
        
    }
 
}
