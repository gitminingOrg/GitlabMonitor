package org.gitmining.monitor.crawler;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetURLConnection {
	
	public static HttpURLConnection getUrlConnection(String urlString){
		try {
			URL url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			//urlConnection.setConnectTimeout(10000);
			//urlConnection.setReadTimeout(10000);
			return urlConnection;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}
