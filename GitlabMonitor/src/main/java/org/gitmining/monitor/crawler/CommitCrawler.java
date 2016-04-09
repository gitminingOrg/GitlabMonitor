package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.Branch;
import org.gitmining.monitor.bean.Project;
import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class CommitCrawler {
	
	public void crawlCommit(){
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		List<Project> projectInfo = projectCrawlerDao.getGroupProject();
		List<String> date = projectCrawlerDao.getDate();
		Map<String, Integer> dataMap = new HashMap<String, Integer>();
		for(int i = 0 ; i < date.size(); i ++){
			dataMap.put(date.get(i), 1);
		}
		boolean judgement = true;
		
		
		for(int m = 0 ; m < projectInfo.size() ; m ++){
			List<Branch> branches = projectCrawlerDao.getBranchByProjectID(projectInfo.get(m).getId());
			for(int n = 0 ; n < branches.size() ; n ++){
				int index = 0;
				String root = "http://114.55.35.12/api/v3/projects/" + projectInfo.get(m).getId() + "/repository/commits?private_token=BzkEVfK_jwk2ytzdx5-h&ref_name=" + branches.get(n).getBranchName() + "&page=";
				HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(root + index);
				BufferedReader reader = null;
				String response = "";
				int responseCode = 200;
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
								if(!projectCrawlerDao.findCommit(projectInfo.get(m).getId(), jsonArray.get(i).getAsJsonObject().get("id").getAsString()) && !jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0].equals(GetDate.getCurrentDate()) && !dataMap.containsKey(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0])){
									String commitURL = "http://114.55.35.12/" + projectInfo.get(m).getPath() + "/commit/" + jsonArray.get(i).getAsJsonObject().get("id").getAsString() + "?private_token=" + GetTokenInfo.getToken();
									urlConnection = GetURLConnection.getUrlConnection(commitURL);
									reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
									String page = "";
									String str = "";
									while((str = reader.readLine()) != null){
										page = page + str;
										if(str.equals("<div class='file-stats js-toggle-content hide'>")){
											break;
										}
									}
									int file = Integer.parseInt(page.split("<a class=\"js-toggle-button\" href=\"#\"><strong>")[1].split(" changed")[0].replace(",", ""));
									int add_line = Integer.parseInt(page.split("<strong class='cgreen'>")[1].split(" additions")[0].replace(",", ""));
									int delete_line = Integer.parseInt(page.split("<strong class='cred'>")[1].split(" deletions")[0].replace(",", ""));
									String id = jsonArray.get(i).getAsJsonObject().get("id").getAsString();
									String author_name = jsonArray.get(i).getAsJsonObject().get("author_name").getAsString();
									String author_email = jsonArray.get(i).getAsJsonObject().get("author_email").getAsString();
									String day = jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0];
									int projectID = projectInfo.get(m).getId();
									
									System.out.println(day + ":" + projectInfo.get(m).getId() + ":" + jsonArray.get(i).getAsJsonObject().get("id").getAsString());
									projectCrawlerDao.insertCommit(id, author_name, author_email, day, add_line, delete_line, file, projectID);
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
					System.err.println(root);
					//projectCrawlerDao.updateLog(GetDate.getCurrentDate());
					e.printStackTrace();
				}
			}
		}
	}
}
