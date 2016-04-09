package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.Project;
import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class FileCrawler {
	
	public void crawlFile(){
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		List<Project> projects = projectCrawlerDao.getGroupProject();
		List<String> date = projectCrawlerDao.getDate();
		Map<String, Integer> dataMap = new HashMap<String, Integer>();
		for(int i = 0 ; i < date.size() - 1; i ++){
			dataMap.put(date.get(i), 1);
		}
		boolean judgement = true;
		
		for(int m = 0 ; m < projects.size() ; m ++){
			int index = 1;
			String root = "http://114.55.35.12/api/v3/projects/" + projects.get(m).getId() + "/events?private_token=" + GetTokenInfo.getToken() + "&page=";
			HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(root + index);
			BufferedReader reader = null;
			String response = "";
			int responseCode = 200;
			ArrayList<String> list = new ArrayList<String>();
			judgement = true;
			
			try {
				responseCode = urlConnection.getResponseCode();
				if(responseCode == 200){
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
					while (response != null && !response.equals("[]")){
						JsonArray jsonArray = new JsonParser().parse(response)
								.getAsJsonArray();
						for(int i = 0 ; i < jsonArray.size() ; i ++){
							if(!(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0].equals(GetDate.getYesterdayDate()) && Integer.parseInt(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[1].split(":")[0]) >= 16) && !(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0].equals(date.get(date.size()-1)) && Integer.parseInt(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[1].split(":")[0]) < 16) && !dataMap.containsKey(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0]) && !jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0].equals(GetDate.getCurrentDate())){
								if(!jsonArray.get(i).getAsJsonObject().get("data").isJsonNull()){
								JsonArray aa = jsonArray.get(i).getAsJsonObject().get("data").getAsJsonObject().get("commits").getAsJsonArray();
									for(int j = 0 ; j < aa.size() ; j ++){
										if(!list.contains(aa.get(j).getAsJsonObject().get("id").getAsString())){
											list.add(aa.get(j).getAsJsonObject().get("id").getAsString());
											if(!projectCrawlerDao.findCommitBySha(aa.get(j).getAsJsonObject().get("id").getAsString())){
												System.err.println(projects.get(m).getId() + ":" + aa.get(j).getAsJsonObject().get("id").getAsString());
											}
											
											JsonArray addList = aa.get(j).getAsJsonObject().get("added").getAsJsonArray();
											JsonArray modifyList = aa.get(j).getAsJsonObject().get("modified").getAsJsonArray();
											JsonArray removeList = aa.get(j).getAsJsonObject().get("removed").getAsJsonArray();
											
											for(int n = 0 ; n < addList.size() ; n ++){
												projectCrawlerDao.insertFile(aa.get(j).getAsJsonObject().get("id").getAsString(), addList.get(n).getAsString(), "code");
											}
											for(int n = 0 ; n < modifyList.size() ; n ++){
												projectCrawlerDao.insertFile(aa.get(j).getAsJsonObject().get("id").getAsString(), modifyList.get(n).getAsString(), "code");
											}
											for(int n = 0 ; n < removeList.size() ; n ++){
												projectCrawlerDao.insertFile(aa.get(j).getAsJsonObject().get("id").getAsString(), removeList.get(n).getAsString(), "code");
											}
											System.out.println(projects.get(m).getId() + ":" + aa.get(j).getAsJsonObject().get("id") + ":" + jsonArray.get(i).getAsJsonObject().get("created_at").getAsString());
										}
									}
								}
							}else if(dataMap.containsKey(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0])){
								judgement = false;
								break;
							}
						}
						if(judgement){
							index ++;
							urlConnection = GetURLConnection.getUrlConnection(root + index);
							reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
							response = reader.readLine();
						}else{
							break;
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println(root + index);
				e.printStackTrace();
			}
			list.clear();
		}
	}
}
