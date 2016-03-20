package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class Test {

    public static void main(String[] args) {
    	ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
    	int index = 0;
		String root = "http://114.55.35.12/api/v3/projects/" + "82" + "/repository/commits?private_token=BzkEVfK_jwk2ytzdx5-h&ref_name=master&page=";
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
						if(!projectCrawlerDao.findCommit(82, jsonArray.get(i).getAsJsonObject().get("id").getAsString())){
							String commitURL = "http://114.55.35.12/" + "141250028_cseiii/cseiii" + "/commit/" + jsonArray.get(i).getAsJsonObject().get("id").getAsString() + "?private_token=" + GetTokenInfo.getToken();
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
							int projectID = 82;
							
							System.out.println(82 + ":" + jsonArray.get(i).getAsJsonObject().get("id").getAsString());
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

}

class MyThread extends Thread {

    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("i = " + i);
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}