package com.imc.mobile.android;


import android.text.Spanned;

public class IMCPost {
	public Spanned Title;
	public String Image ;
	public Spanned Excerpt;
	public String CommentCount;
	public String LikeCount;
	public String PostID;
	public String dateposted;
	public String Content;
	public String Author;
	public String ZillaLikeCnt;
	public String PostUrl;
	
	public String getPostUrl(String _postUrl)
	{
		return _postUrl;
	}
	public void setPostUrl(String _postUrl)
	{
		this.ZillaLikeCnt = _postUrl;
	}
	
	public String getZillaLikeCnt(String _zilla)
	{
		return _zilla;
	}
	public void setZillaLikeCnt(String _zilla)
	{
		this.ZillaLikeCnt = _zilla;
	}
	
	public String getAuthor(String _author)
	{
		return _author;
	}
	public void setAuthor(String _author)
	{
		this.Author = _author;
	}
	public String getContent(String _content)
	{
		return _content;
	}
	public void setContent(String _content)
	{
		this.Content = _content;
	}
	public String getdatePosted(String _datePosted)
	{
		return _datePosted;
	}
	public void setdatePosted(String _datePosted)
	{
		this.dateposted = _datePosted;
	}
	
	public String getPostID(String _postID)
	{
		return _postID;
	}
	public void setPostID(String _postID)
	{
		this.PostID = _postID;
	}
	
	public Spanned getTitle(Spanned _title)
	{
		return _title;
	}
	public void setTitle (Spanned _title){
		this.Title = _title;
	}
	public String getImage(String _image)
	{
		return _image;
	}
	public void setImage (String _image)
	{
		this.Image = _image;
	}
	public String getCommentCount(String _commentCount)
	{
			return _commentCount;
	}
	public void setCommentCount(String _commentCount)
	{
			this.CommentCount = _commentCount;
	}
	public String getlikeCount(String _likeCount)
	{
		return _likeCount;
	}
	public void setLikeCount(String _likeCount)
	{
		this.LikeCount = _likeCount;
	}
	
	public Spanned getExcerpt(Spanned _excerpt)
	{
		return _excerpt;
	}
	public void setExcerpt (Spanned _excerpt){
		this.Excerpt = _excerpt;
	}

}
