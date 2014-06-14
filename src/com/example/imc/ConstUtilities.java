package com.example.imc;

import java.util.ArrayList;
import java.util.HashMap;

public class ConstUtilities {
	
	
	//API 
	public static String getPosts = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts?number=14&context=default&pretty=true";
	public static String getNumberOfPosts = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/posts?after=%s&pretty=1";
	public static String getComments = "https://public-api.wordpress.com/rest/v1/sites/www.indianmomsconnect.com/comments?number=25&pretty=1";
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
	public static final String Node_PostUrl = "URL";
	//For Comments
	public static final String Node_CmtContent = "content";
	public static final String Node_CmtAvatarUrl = "avatar_URL";
	public static final String Node_CmtName = "name";
	public static final String Node_CmtType = "type";
	public static final String Node_Comments = "comments";
	public static final String Node_CmtPosts = "post";
	public static final String Node_PostID = "ID";
	public static final String Node_AvatarBitmap = "AvatarBitmap";
	public static final String Position ="0";
	public static final String Zilla_Like = "ZillaLike";
	
	public static  ArrayList<HashMap<String,Object>> postLists = null;
	
	

}
