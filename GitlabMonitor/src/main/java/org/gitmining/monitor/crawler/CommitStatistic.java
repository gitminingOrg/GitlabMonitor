package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitmining.monitor.bean.Commit;
import org.gitmining.monitor.bean.ProjectCommit;
import org.gitmining.monitor.bean.StudentCommit;
import org.gitmining.monitor.crawlerdao.ProjectCrawlerDao;
import org.gitmining.monitor.crawlerdao.StudentCrawlerDao;

public class CommitStatistic {
	
	public void countProjectCommit(){
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		projectCrawlerDao.truncateProjectCommit();
		List<ProjectCommit> lists = projectCrawlerDao.getProjectCommit();
		for(int i = 0 ; i < lists.size() ; i ++){
			projectCrawlerDao.insertProjectCommit(lists.get(i));
		}
		Map<Integer, String> map = projectCrawlerDao.getIDAndTeam();
		try {
			List<String> date = projectCrawlerDao.getDate();
			for(int k = 0 ; k < date.size() - 1 ; k ++){
				String day = date.get(k);
				for(int key : map.keySet()){
					if(!projectCrawlerDao.findProjectCommit(key, day)){
						ProjectCommit projectCommit = new ProjectCommit();
						projectCommit.setId(key);
						projectCommit.setTeam(map.get(key).split("/")[0]);
						projectCommit.setDay(day);
						projectCommit.setCommit_count(0);
						projectCommit.setAdd_line(0);
						projectCommit.setDelete_line(0);
						projectCommit.setJava_file(0);
						projectCommit.setTotal_commit(0);
						projectCommit.setTotal_add(0);
						projectCommit.setTotal_delete(0);
						projectCrawlerDao.insertProjectCommit(projectCommit);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			projectCrawlerDao.updateLog(GetDate.getCurrentDate());
			e.printStackTrace();
		}
	}
	
	public void countStudentCommit(){
		StudentCrawlerDao studentCrawlerDao = new StudentCrawlerDao();
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		studentCrawlerDao.truncateStudentCommit();
		List<StudentCommit> lists = studentCrawlerDao.getStudentCommit();
		for(int i = 0 ; i < lists.size() ; i ++){
			studentCrawlerDao.insertStudentCommmit(lists.get(i));
		}
		List<Object> list = studentCrawlerDao.getStudentIDAndName();
		List<Integer> ids = (List<Integer>)list.get(0);
		List<String> students = (List<String>)list.get(1);
		try {
			List<String> date = projectCrawlerDao.getDate();
			for(int k = 0 ; k < date.size() - 1 ; k ++){
				String day = date.get(k);
				for(int i = 0 ; i < ids.size() ; i ++){
					if(!studentCrawlerDao.findStudentCommit(ids.get(i), students.get(i), day)){
						StudentCommit studentCommit = new StudentCommit();
						studentCommit.setId(ids.get(i));
						studentCommit.setStudent(students.get(i));
						studentCommit.setDay(day);
						studentCommit.setCommit_count(0);
						studentCommit.setAdd_line(0);
						studentCommit.setDelete_line(0);
						studentCommit.setJava_file(0);
						studentCommit.setTotal_commit(0);
						studentCommit.setTotal_add(0);
						studentCommit.setTotal_delete(0);
						studentCrawlerDao.insertStudentCommmit(studentCommit);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			projectCrawlerDao = new ProjectCrawlerDao();
			projectCrawlerDao.updateLog(GetDate.getCurrentDate());
			e.printStackTrace();
		}
	}
	
	public void countDayHourMap(){
		ProjectCrawlerDao projectCrawlerDao = new ProjectCrawlerDao();
		List<Commit> commits = projectCrawlerDao.getCommits();
		Map<String, Map<String, Integer>> map = new HashMap<String, Map<String,Integer>>();
		String[] days = {"Su","Mo","Tu","We","Th","Fr","Sa"};
		String[] hours = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
		
		//initial
		for(int i = 0 ; i < days.length ; i ++){
			Map<String, Integer> hoursInitial = new HashMap<String, Integer>();
			for(int j = 0 ; j < hours.length ; j ++){
				hoursInitial.put(hours[j], 0);
			}
			map.put(days[i], hoursInitial);
		}
		
		for(int i = 0 ; i < commits.size() ; i ++){
			Commit temp = commits.get(i);
			
			Calendar c = Calendar.getInstance(java.util.Locale.CHINA);
			String[] sp = temp.getDay().split("T")[0].split("-");
			c.set(Calendar.YEAR,Integer.parseInt(sp[0]));
			c.set(Calendar.MONTH,Integer.parseInt(sp[1])-1);
			c.set(Calendar.DATE,Integer.parseInt(sp[2]));

			int wd = c.get(Calendar.DAY_OF_WEEK);
			String x = "";
			switch(wd){
			case 1:x="Su";break;
			case 2:x="Mo";break;
			case 3:x="Tu";break;
			case 4:x="We";break;
			case 5:x="Th";break;
			case 6:x="Fr";break;
			case 7:x="Sa";break;
			}
			
			String hour = temp.getDay().split("T")[1].split(":")[0];
			map.get(x).put(hour, map.get(x).get(hour) + 1);
		}
		
		for(String key : map.keySet()){
			for(String hourKey : map.get(key).keySet()){
				projectCrawlerDao.insertDayHour(key, hourKey, map.get(key).get(hourKey));
			}
		}
	}
}
