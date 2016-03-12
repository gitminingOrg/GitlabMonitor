package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.gitmining.monitor.crawlerdao.StudentCrawlerDao;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class GroupCrawler {
	public void crawlGroup(){
		int index = 1;
		String root = "http://114.55.35.12/api/v3/groups?private_token=" + GetTokenInfo.getToken() + "&page=";
		HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(root + index);
		BufferedReader reader = null;
		String response = "";
		int responseCode = 200;
		StudentCrawlerDao studentCrawlerDao = new StudentCrawlerDao();
		
		try {
			responseCode = urlConnection.getResponseCode();
			if(responseCode == 200){
				reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
				response = reader.readLine();
				while (response != null && !response.equals("[]")){
					JsonArray jsonArray = new JsonParser().parse(response)
							.getAsJsonArray();
					for(int i = 0 ; i < jsonArray.size() ; i ++){
						int id = jsonArray.get(i).getAsJsonObject().get("id").getAsInt();
						String name = jsonArray.get(i).getAsJsonObject().get("name").getAsString();
						if(!studentCrawlerDao.findTeam(id, name)){
							studentCrawlerDao.insertTeamInfo(id, name);
						}
						
						String groupmemberurl = "http://114.55.35.12/api/v3/groups/" + id + "/members?private_token=BzkEVfK_jwk2ytzdx5-h";
						urlConnection = GetURLConnection.getUrlConnection(groupmemberurl);
						reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
						response = reader.readLine();
						if(response != null && !response.equals("[]")){
							JsonArray memberArray = new JsonParser().parse(response)
									.getAsJsonArray();
							for(int j = 0 ; j < memberArray.size() ; j ++){
								int memberID = memberArray.get(j).getAsJsonObject().get("id").getAsInt();
								String memberName = memberArray.get(j).getAsJsonObject().get("name").getAsString();
								if(!studentCrawlerDao.findStudent(memberID, memberName, name)){
									studentCrawlerDao.insertMemberInfo(memberID, memberName, name);
								}
							}
						}
					}
					index ++;
					urlConnection = GetURLConnection.getUrlConnection(root + index);
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
