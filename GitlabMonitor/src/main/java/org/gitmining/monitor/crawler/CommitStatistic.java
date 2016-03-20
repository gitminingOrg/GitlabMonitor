package org.gitmining.monitor.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
