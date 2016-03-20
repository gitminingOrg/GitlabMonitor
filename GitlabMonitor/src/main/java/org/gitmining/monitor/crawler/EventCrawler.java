package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import org.gitmining.monitor.bean.Branch;
import org.gitmining.monitor.bean.Project;
import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class EventCrawler {
	
	public void crawlEvent(){
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		List<Project> projectInfo = projectCrawlerDao.getGroupProject();
		//List<String> date = projectCrawlerDao.getDate();
		
		for(int m = 0 ; m < projectInfo.size() ; m++){
			int index = 1;
			String root = "http://114.55.35.12/api/v3/projects/" + projectInfo.get(m).getId() + "/events?private_token=BzkEVfK_jwk2ytzdx5-h&page=";
			HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(root + index);
			BufferedReader reader = null;
			String response = "";
			int responseCode = 200;
			
			try {
				responseCode = urlConnection.getResponseCode();
				if(responseCode == 200){
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
					while (response != null && !response.equals("[]")){
						JsonArray jsonArray = new JsonParser().parse(response)
								.getAsJsonArray();
						for(int i = 0 ; i < jsonArray.size() ; i ++){
							String action_name = jsonArray.get(i).getAsJsonObject().get("action_name").getAsString();
							String author_name = jsonArray.get(i).getAsJsonObject().get("author_username").getAsString();
							String day = jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0];
							int projectID = projectInfo.get(m).getId();
							
							System.out.println(projectInfo.get(m).getId() + ":" + jsonArray.get(i).getAsJsonObject().get("action_name").getAsString() + "------" + jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0]);
							//projectCrawlerDao.insertCommit(id, author_name, author_email, day, add_line, delete_line, file, projectID);
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
}
