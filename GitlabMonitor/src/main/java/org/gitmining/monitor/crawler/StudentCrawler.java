package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class StudentCrawler {
	public void crawlStudent(){
		int index = 1;
		String root = "http://114.55.35.12/api/v3/users?private_token=" + GetTokenInfo.getToken() + "&page=";
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
						System.out.println(jsonArray.get(i).getAsJsonObject().get("bio").toString());
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
	
	public static void main(String[] args){
		/*System.out.println("start update!");
		GroupCrawler groupCrawler = new GroupCrawler();
		GroupProjectCrawler groupProjectCrawler = new GroupProjectCrawler();
		BranchCrawler branchCrawler = new BranchCrawler();
		CommitCrawler commitCrawler = new CommitCrawler();
		CommitStatistic commitStatistic = new CommitStatistic();
		
		groupCrawler.crawlGroup();
		groupProjectCrawler.crawlGroupProject();
		branchCrawler.crawlBranch();
		commitCrawler.crawlCommit();
		commitStatistic.countProjectCommit();
		commitStatistic.countStudentCommit();*/
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		List<String> date = new ArrayList<String>();
		
		try {
			FileReader fileReader = new FileReader(new File("Log.txt"));
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String dateString = "";
			while((dateString = bufferedReader.readLine()) != null){
				date.add(dateString);
			}
			bufferedReader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		for(int i = 0 ; i < date.size() ; i ++){
			projectCrawlerDao.insertUpdateLog(date.get(i), 1, 1);
		}
	}
}
