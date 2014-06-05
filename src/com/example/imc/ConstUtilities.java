package com.example.imc;

public class ConstUtilities {
	
	
	//API 
	public static String getPosts = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts?number=5&context=default&pretty=true";
	public static String getNumberOfPosts = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts?after=%s&pretty=1";
	public static String getComments = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/comments?number=5&pretty=1";
	public static String getSinglePost = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts/";
	//JSON Node Names
	//For Posts
	public static final String Node_Found = "found";
	public static final String Node_Posts = "posts";
	public static final String Node_Title = "title";
	public static final String Node_Excerpt = "excerpt";
	public static final String Node_Image = "featured_image";
	public static final String Node_CommentCnt = "comment_count";
	public static final String Node_LikeCnt = "like_count";
	public static final String Node_ID = "ID";
	public static final String Node_Date = "date";
	public static final String Node_Content = "content";
	public static final String Node_Author = "author";
	//For Comments
	public static final String Node_CmtContent = "content";
	public static final String Node_CmtAvatarUrl = "avatar_URL";
	public static final String Node_CmtName = "name";
	public static final String Node_CmtType = "type";
	public static final String Node_Comments = "comments";
	public static final String Node_CmtPosts = "post";
	public static final String Node_PostID = "ID";
	public static final String Node_AvatarBitmap = "AvatarBitmap";
	
	

}
