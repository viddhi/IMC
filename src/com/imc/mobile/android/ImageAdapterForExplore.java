package com.imc.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.imc.mobile.android.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

 
@SuppressLint("InflateParams")
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
    	  int imgID = mContext.getResources().getIdentifier("misc","drawable", mContext.getPackageName());
    	  
          if(convertView==null)
              vi = inflater.inflate(R.layout.gridlist_item, null);
         HashMap<String, Object> category = new HashMap<String, Object>();
         category = data.get(position);
         TextView tv = (TextView)vi.findViewById(R.id.txtCategory); 
         String CategoryName = category.get(ConstUtilities.Node_CategoryName).toString();
         String PostCount = category.get(ConstUtilities.Node_PostCount).toString();
         String Slug = category.get("slug").toString();
         Slug = Slug.replace("'", "");
         Slug = Slug.replace('-', '_');
         WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
         Display display = wm.getDefaultDisplay();
         Point size = new Point();
         display.getSize(size);
       //  int Width = 322;
        tv.setText("   " + CategoryName + "\n" +"   " + PostCount + " " + "Posts");
        tv.setGravity(Gravity.CENTER_VERTICAL);
        tv.setTextColor(Color.WHITE);
        
        try
        {
         imgID = mContext.getResources().getIdentifier(Slug,"drawable", mContext.getPackageName());
         if(imgID == 0)
         {
        	 imgID = mContext.getResources().getIdentifier("misc","drawable", mContext.getPackageName());
         }
         Drawable dr = mContext.getResources().getDrawable(imgID);
         Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
         Drawable d = new BitmapDrawable(mContext.getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));
         tv.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
         tv.setPadding(20, 0, 0, 0);
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
      
         Typeface face=Typeface.createFromAsset(mContext.getAssets(), "Roboto-Light.ttf"); 
         tv.setTypeface(face); 
        }
        catch(Exception ex)
        {
        	System.out.println("not found");
        }
        
       
        return vi;
     
        
    }
 
}
