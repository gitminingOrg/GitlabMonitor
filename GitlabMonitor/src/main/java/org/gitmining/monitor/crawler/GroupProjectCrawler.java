package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.List;

import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;
import org.gitmining.monitor.crawlerdao.StudentCrawlerDao;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class GroupProjectCrawler {
	public void crawlGroupProject(){
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		StudentCrawlerDao studentCrawlerDao = new StudentCrawlerDao();
		List<Integer> groupID = studentCrawlerDao.getTeamID();
		for(int m = 0 ; m < groupID.size() ; m++){
			int index = 1;
			String root = "http://114.55.35.12/api/v3/groups/" + groupID.get(m) + "/projects?private_token=BzkEVfK_jwk2ytzdx5-h&page=";
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
							int id = jsonArray.get(i).getAsJsonObject().get("id").getAsInt();
							String name = jsonArray.get(i).getAsJsonObject().get("name_with_namespace").getAsString().replace(" / ", "/");
							String path = jsonArray.get(i).getAsJsonObject().get("path_with_namespace").getAsString();
							int groupid = groupID.get(m);
							if(!projectCrawlerDao.findProjectByID(id)){
								projectCrawlerDao.insertGroupProjectInfo(id, name, path, groupid);
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
}
