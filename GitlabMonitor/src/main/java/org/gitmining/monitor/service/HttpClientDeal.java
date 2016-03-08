package org.gitmining.monitor.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientDeal {
//	http://114.55.35.12/api/v3/users?access_token=BzkEVfK_jwk2ytzdx5-h
	
	public static void main(String[] args){
		new HttpClientDeal().getHttpResponse("http://114.55.35.12/api/v3/users?private_token=BzkEVfK_jwk2ytzdx5-h");
		
	}
	public String getHttpResponse(String uri){
		String result = null;
		try{
			HttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet(uri);
			HttpResponse response = client.execute(get);
			result =EntityUtils.toString(response.getEntity());
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public int getHttpResponseCode(String uri){
		int result = 0;
		try{
			HttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet(uri);
			HttpResponse response = client.execute(get);
			result =response.getStatusLine().getStatusCode();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean getHttpTokenStatus(String token){
		String head = "http://114.55.35.12/api/v3/users?access_token=";
		if(getHttpResponseCode(head+token) == 200){
			return true;
		}else{
			return false;
		}
	}
}
