package com.example.imc;
import android.graphics.Bitmap;
import android.text.Spanned;
public class IMCComment {
	public Spanned Commentor;
	public String AvatarUrl ;
	public Spanned Comment;
	public int PostID;
	public Bitmap imgBitmap;
	
	public Spanned getCommentor(Spanned _commentor)
	{
		return _commentor;
	}
	public void setCommentor (Spanned _commentor){
		this.Commentor = _commentor;
	}
	public Spanned getComment(Spanned _comment)
	{
		return _comment;
	}
	public void setComment (Spanned _comment){
		this.Comment = _comment;
	}
	public String getAvatarUrl(String _avatarUrl)
	{
		return _avatarUrl;
	}
	public void setAvatarUrl (String _avatarUrl){
		this.AvatarUrl = _avatarUrl;
	}
	public void getPostID(int _postID)
	{
		this.PostID = _postID;
	}
	public int setPostID(int _postID)
	{
		return _postID;
	}
	public void getimgBmp(Bitmap _imgBmp)
	{
		this.imgBitmap = _imgBmp;
	}
	public Bitmap setimgBmp(Bitmap _imgBmp)
	{
		return _imgBmp;
	}
	
	
}
