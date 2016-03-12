package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import org.gitmining.monitor.bean.Project;
import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class BranchCrawler {
	
	public void crawlBranch(){
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		List<Project> list = projectCrawlerDao.getGroupProject();
		
		for(int i = 0 ; i < list.size() ; i ++){
			String root = "http://114.55.35.12/api/v3/projects/" + list.get(i).getId() + "/repository/branches?private_token=BzkEVfK_jwk2ytzdx5-h";
			HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(root);
			BufferedReader reader = null;
			String response = "";
			int responseCode = 200;
			
			try {
				responseCode = urlConnection.getResponseCode();
				if(responseCode == 200){
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
					if(response != null && !response.equals("[]")){
						JsonArray jsonArray = new JsonParser().parse(response)
								.getAsJsonArray();
						for(int j = 0 ; j < jsonArray.size() ; j ++){
							if(projectCrawlerDao.findBranch(list.get(i).getId(), jsonArray.get(j).getAsJsonObject().get("name").getAsString())){
								projectCrawlerDao.insertBranch(list.get(i).getId(), jsonArray.get(j).getAsJsonObject().get("name").getAsString());
							}
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
