package com.imc.mobile.android;

import java.util.ArrayList;
import java.util.HashMap;

import com.imc.mobile.android.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class IMCCmtAdapter extends BaseAdapter{
 
    private Activity activity;
    private ArrayList<HashMap<String,Object>> data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    public Typeface face;
 
    public IMCCmtAdapter(Activity a, ArrayList<HashMap<String,Object>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        face=Typeface.createFromAsset(a.getAssets(), "Roboto-Light.ttf"); 
        imageLoader=new ImageLoader(a);
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
            vi = inflater.inflate(R.layout.commentlist_item, null);
 
        TextView title = (TextView)vi.findViewById(R.id.Commentor); 
        TextView FullComment = (TextView)vi.findViewById(R.id.Comment); 
        TextView fullContent = (TextView)vi.findViewById(R.id.FullContent); 
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.Avatar); 
        TextView AvatarURL = (TextView)vi.findViewById(R.id.AvatarUrl); 
        TextView PostID = (TextView)vi.findViewById(R.id.PostID); 
        
        HashMap<String, Object> cmt = new HashMap<String, Object>();
        cmt = data.get(position);
 
        // Setting all values in listview
        title.setText(cmt.get(ConstUtilities.Node_CmtName).toString());
        String commentText = cmt.get(ConstUtilities.Node_CmtContent).toString();
        if(commentText.length() > 200)
        {
        	commentText = commentText.substring(0, 200) + "...";
        }
        
        FullComment.setText(commentText);
        fullContent.setText(cmt.get(ConstUtilities.Node_CmtContent).toString());
        AvatarURL.setText(cmt.get(ConstUtilities.Node_CmtAvatarUrl).toString());
        PostID.setText(cmt.get(ConstUtilities.Node_PostID).toString());
        //setting custom font
        title.setTypeface(face);
        FullComment.setTypeface(face);
        fullContent.setTypeface(face);
        AvatarURL.setTypeface(face);
        
        imageLoader.DisplayImage(cmt.get(ConstUtilities.Node_CmtAvatarUrl).toString(), thumb_image);
        return vi;
    }
}
