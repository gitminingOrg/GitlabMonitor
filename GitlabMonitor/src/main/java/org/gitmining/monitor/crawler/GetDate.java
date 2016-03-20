package org.gitmining.monitor.crawler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetDate {

	public static String getCurrentDate(){
		Calendar calendar = Calendar.getInstance();
    	Date date = calendar.getTime();
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	return(df.format(date).toString());
	}
	
	public static String getYesterdayDate(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
    	Date date = calendar.getTime();
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	return(df.format(date).toString());
	}
}
