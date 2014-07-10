package com.example.imc;


public class IMCCategory  {
	public int CategoryID;
	public String CategoryName;
	public String PostCount;
	
	
	
	public String getCategoryName(String _categoryName)
	{
		return _categoryName;
	}
	public void setCategoryName(String _categoryName){
		this.CategoryName = _categoryName;
	}
	public void getCategoryID(int _categoryID)
	{
		this.CategoryID = _categoryID;
	}
	public int setCategoryID(int _categoryID)
	{
		return _categoryID;
	}
	public String getPostCount(String _postCount)
	{
		return _postCount;
	}
	public void setPostCount(String _postCount){
		this.PostCount = _postCount;
	}
	
	
}
