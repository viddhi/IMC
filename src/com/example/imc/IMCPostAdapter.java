package com.example.imc;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IMCPostAdapter extends BaseAdapter{
 
    private Activity activity;
    private ArrayList<HashMap<String,Object>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    public Typeface face;
 
    public IMCPostAdapter(Activity a, ArrayList<HashMap<String,Object>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        face=Typeface.createFromAsset(a.getAssets(), "Roboto-Light.ttf"); 
        imageLoader=new ImageLoader(activity.getApplicationContext());
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
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.postlist_item, null);
 
        TextView title = (TextView)vi.findViewById(R.id.Title); 
        TextView excerpt = (TextView)vi.findViewById(R.id.Excerpt); 
        TextView PostID = (TextView)vi.findViewById(R.id.ID); 
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.PostImg); 
        TextView CmtCnt = (TextView)vi.findViewById(R.id.CmtCnt); 
        TextView LikeCnt = (TextView)vi.findViewById(R.id.LikeCnt); 
 
        HashMap<String, Object> post = new HashMap<String, Object>();
        post = data.get(position);
 
        // Setting all values in listview
        title.setText(post.get(ConstUtilities.Node_Title).toString());
        excerpt.setText(post.get(ConstUtilities.Node_Excerpt).toString());
        PostID.setText(post.get(ConstUtilities.Node_ID).toString());
        CmtCnt.setText(post.get(ConstUtilities.Node_CommentCnt).toString());
        LikeCnt.setText(post.get(ConstUtilities.Node_LikeCnt).toString());
        //Setting up font
        title.setTypeface(face);
        excerpt.setTypeface(face);
        CmtCnt.setTypeface(face);
        LikeCnt.setTypeface(face);
        imageLoader.DisplayImage(post.get(ConstUtilities.Node_Image).toString(), thumb_image);
        return vi;
    }
}

