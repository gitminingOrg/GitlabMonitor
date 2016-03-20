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

import javax.enterprise.inject.New;

import org.gitmining.monitor.bean.Branch;
import org.gitmining.monitor.bean.Project;
import org.gitmining.monitor.bean.Student;
import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;
import org.gitmining.monitor.crawlerdao.StudentCrawlerDao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
		System.out.println("start update!");
		GroupCrawler groupCrawler = new GroupCrawler();
		GroupProjectCrawler groupProjectCrawler = new GroupProjectCrawler();
		BranchCrawler branchCrawler = new BranchCrawler();
		CommitCrawler commitCrawler = new CommitCrawler();
		CommitStatistic commitStatistic = new CommitStatistic();
		
		//groupCrawler.crawlGroup();
		//groupProjectCrawler.crawlGroupProject();
		//branchCrawler.crawlBranch();
		//commitCrawler.crawlCommit();
		//commitStatistic.countProjectCommit();
		//commitStatistic.countStudentCommit();
		
		StudentCrawler studentCrawler = new StudentCrawler();
		studentCrawler.crawlFile();
	}
	
	public void crawlFile(){
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		List<Project> projects = projectCrawlerDao.getGroupProject();
		
		for(int m = 0 ; m < projects.size() ; m ++){
			int index = 1;
			String root = "http://114.55.35.12/api/v3/projects/" + projects.get(m).getId() + "/events?private_token=" + GetTokenInfo.getToken() + "&page=";
			HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(root + index);
			BufferedReader reader = null;
			String response = "";
			int responseCode = 200;
			ArrayList<String> list = new ArrayList<String>();
			
			try {
				responseCode = urlConnection.getResponseCode();
				if(responseCode == 200){
					reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
					response = reader.readLine();
					while (response != null && !response.equals("[]")){
						JsonArray jsonArray = new JsonParser().parse(response)
								.getAsJsonArray();
						for(int i = 0 ; i < jsonArray.size() ; i ++){
							if(!jsonArray.get(i).getAsJsonObject().get("data").isJsonNull() && !jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0].equals("2016-03-18") && !jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0].equals("2016-03-19") && !(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0].equals("2016-03-17") && Integer.parseInt(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[1].split(":")[0]) >= 16)){
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
				System.err.println(root + index);
				e.printStackTrace();
			}
			list.clear();
		}
	}
	
	public void crawlCommit(){
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		List<Project> projectInfo = projectCrawlerDao.getGroupProject();
		List<String> date = projectCrawlerDao.getDate();
		Map<String, Integer> dataMap = new HashMap<String, Integer>();
		for(int i = 0 ; i < date.size() - 2 ; i ++){
			dataMap.put(date.get(i), 1);
		}
		boolean judgement = true;
		
		
		//for(int m = 0 ; m < projectInfo.size() ; m ++){
			//List<Branch> branches = projectCrawlerDao.getBranchByProjectID(projectInfo.get(m).getId());
			List<Branch> branches = projectCrawlerDao.getBranchByProjectID(108);
			for(int n = 0 ; n < branches.size() ; n ++){
				int index = 0;
				//String root = "http://114.55.35.12/api/v3/projects/" + projectInfo.get(m).getId() + "/repository/commits?private_token=BzkEVfK_jwk2ytzdx5-h&ref_name=" + branches.get(n).getBranchName() + "&page=";
				String root = "http://114.55.35.12/api/v3/projects/" + 108 + "/repository/commits?private_token=BzkEVfK_jwk2ytzdx5-h&ref_name=" + branches.get(n).getBranchName() + "&page=";
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
								//if(!projectCrawlerDao.findCommit(projectInfo.get(m).getId(), jsonArray.get(i).getAsJsonObject().get("id").getAsString())){
								if(!projectCrawlerDao.findCommit(108, jsonArray.get(i).getAsJsonObject().get("id").getAsString()) && !jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0].equals("2016-03-18") && !jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0].equals("2016-03-19")){
									//String commitURL = "http://114.55.35.12/" + projectInfo.get(m).getPath() + "/commit/" + jsonArray.get(i).getAsJsonObject().get("id").getAsString() + "?private_token=" + GetTokenInfo.getToken();
									String commitURL = "http://114.55.35.12/" + "141250183_cseiii_AnyQuant/AnyQuent" + "/commit/" + jsonArray.get(i).getAsJsonObject().get("id").getAsString() + "?private_token=" + GetTokenInfo.getToken();
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
									//int projectID = projectInfo.get(m).getId();
									int projectID = 108;
									
									//System.out.println(projectInfo.get(m).getId() + ":" + jsonArray.get(i).getAsJsonObject().get("id").getAsString());
									System.out.println(108 + ":" + jsonArray.get(i).getAsJsonObject().get("id").getAsString());
									projectCrawlerDao.insertCommit(id, author_name, author_email, day, add_line, delete_line, file, projectID);
								}/*else if(dataMap.containsKey(jsonArray.get(i).getAsJsonObject().get("created_at").getAsString().split("T")[0])){
									judgement = false;
									break;
								}*/
							}
							/*if(judgement){*/
								index ++;
								urlConnection = GetURLConnection.getUrlConnection(root + index);
								reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
								response = reader.readLine();
							/*}else{
								break;
							}*/
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					projectCrawlerDao.updateLog(GetDate.getCurrentDate());
					e.printStackTrace();
				}
			}
		//}
	}
}



/*StudentCrawlerDao studentCrawlerDao = new StudentCrawlerDao();
List<Integer> ids = studentCrawlerDao.getStudentID();

for(int i = 0 ; i < ids.size() ; i ++){
	String root = "http://114.55.35.12/api/v3/users/"+ ids.get(i) +"?private_token=" + GetTokenInfo.getToken();
	HttpURLConnection urlConnection = GetURLConnection.getUrlConnection(root);
	BufferedReader reader = null;
	String response = "";
	int responseCode = 200;
	
	try {
		responseCode = urlConnection.getResponseCode();
		if(responseCode == 200){
			reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"utf-8"));
			response = reader.readLine();
			if (response != null && !response.equals("[]")){
				JsonObject object = new JsonParser().parse(response)
						.getAsJsonObject();
				int id = ids.get(i);
				String name = object.get("name").getAsString();
				String web_url = object.get("web_url").getAsString();
				String created_at = object.get("created_at").getAsString();
				String bio = "";
				if(!object.get("bio").isJsonNull()){
					bio = object.get("bio").getAsString();
				}
				String email = object.get("email").getAsString();
				
				Student student = new Student();
				student.setId(i);
				student.setName(name);
				student.setWeb_url(web_url);
				student.setCreated_at(created_at);
				student.setBio(bio);
				student.setEmail(email);
				
				studentCrawlerDao.insertStudentInfo(student);
			}
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}*/



/*
 * 16:6de78a9a7c9db938f262febc63fa894c422a4a0c
16:23b0b1d82371ec58d5127b3aa710a28ca4e4770c
16:c2d40e0bfcb2f07bd6def039cd1019893f76f12b
16:0070db507f967cead84e840ca716a0d2dcbe747f
16:040a2460e01077292b0adada85e9c01be6b10d7c
34:20d9040618f5da129c3db02c183c2dd6001fdd74
53:1dc2f7213d47a5820da3e0aa90a2c763da6e80c7
87:9a4fa3264a390b73d8c2bbc6653f7cb755c7e904
114:4878d40dabb4d78ff2a4ba329fb5adf4fcfe052a
114:2547644dd8f641c97b8262c7c81d1964a779ec6c
129:5ec7449dc953b462740e60830c05006eb2e436cd
129:30153b92ab0562b0b20c9c0ba526ad22285f5250
129:3551c0689b21650219590d207014936d0c84b9b3
129:8db8985b2593aafa8b4b95ce87603b9b54a23b04
129:9a06d8d0cb8360b381c4ada3e9fabb8530b3836a
129:d05c1131030309d2a1e1356ceed2fd6ec528e2d9
129:b336f666f5ae0ea12484b57051aaa4db61b439f2
136:ec4b530075182a357506ab61442c651719950f0e
136:2bac5092feb5fd0224c47c3cd0e2c2a176504fb5
136:9f8dc1a37cadc753182a6a26399a2d9537bb8319
136:b38d9e01f07387466d5cd22ccef538326578b8fb
136:cf1c8173a9a29c69273aec4bb2d76c8a44a2a575
  */
