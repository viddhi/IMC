package com.example.imc;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
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
 
    public IMCCmtAdapter(Activity a, ArrayList<HashMap<String,Object>> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            vi = inflater.inflate(R.layout.commentlist_item, null);
 
        TextView title = (TextView)vi.findViewById(R.id.Commentor); // title
        TextView artist = (TextView)vi.findViewById(R.id.Comment); // artist name
        TextView avatarurl = (TextView)vi.findViewById(R.id.Avatar_Url); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.Avatar); // thumb image
 
        HashMap<String, Object> cmt = new HashMap<String, Object>();
        cmt = data.get(position);
 
        // Setting all values in listview
        title.setText(cmt.get(ConstUtilities.Node_CmtName).toString());
        artist.setText(cmt.get(ConstUtilities.Node_CmtContent).toString());
        avatarurl.setText(cmt.get(ConstUtilities.Node_CmtAvatarUrl).toString());
        imageLoader.DisplayImage(cmt.get(ConstUtilities.Node_CmtAvatarUrl).toString(), thumb_image);
        return vi;
    }
}
