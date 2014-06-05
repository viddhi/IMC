package com.example.imc;

import java.net.URLDecoder;

public class UtilFunctions {

	public static String stringCleanup(String incomingString)
	{
		try
		{
		 String Temp = URLDecoder.decode(incomingString,"UTF-8");
		 Temp = Temp.replaceAll( "[\\u202C\\u202A]", "" );
		 return Temp;
		}
		catch(Exception ex)
		{
			return incomingString;
		}
	}
	@SuppressWarnings("null")
	public static String generateDate(String Date)
	{
		StringBuilder returnDate = null;
		
		String[] dateArray = Date.split("-");
		returnDate = returnDate.append(dateArray[2]);
		if(dateArray[1] == "01")
		{
			returnDate = returnDate.append(" January ");
		}
		if(dateArray[1] == "02")
		{
			returnDate = returnDate.append(" February ");
		}
		if(dateArray[1] == "03")
		{
			returnDate = returnDate.append(" March ");
		}
		if(dateArray[1] == "04")
		{
			returnDate = returnDate.append(" April ");
		}
		if(dateArray[1] == "05")
		{
			returnDate = returnDate.append(" May ");
		}
		if(dateArray[1] == "06")
		{
			returnDate = returnDate.append(" June ");
		}
		if(dateArray[1] == "07")
		{
			returnDate = returnDate.append(" July ");
		}
		if(dateArray[1] == "08")
		{
			returnDate = returnDate.append(" August ");
		}
		if(dateArray[1] == "09")
		{
			returnDate = returnDate.append(" September ");
		}
		if(dateArray[1] == "10")
		{
			returnDate = returnDate.append(" October ");
		}
		if(dateArray[1] == "11")
		{
			returnDate = returnDate.append(" November ");
		}
		if(dateArray[1] == "12")
		{
			returnDate = returnDate.append(" December ");
		}
		returnDate = returnDate.append(dateArray[0]);
		return returnDate.toString();
		
	}
}
