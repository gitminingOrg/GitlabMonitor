package org.gitmining.monitor.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.gitmining.monitor.bean.ProjectEvent;
import org.gitmining.monitor.crawler.BranchCrawler;
import org.gitmining.monitor.crawler.CommitCrawler;
import org.gitmining.monitor.crawler.CommitStatistic;
import org.gitmining.monitor.crawler.GroupCrawler;
import org.gitmining.monitor.crawler.GroupProjectCrawler;
import org.gitmining.monitor.dao.ProjectDao;
import org.gitmining.monitor.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation=Propagation.SUPPORTS, readOnly=true)
public class UpdateDataService {
	@Autowired
	private ProjectDao projectDao;
	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}
	@Autowired
	private StudentDao studentDao; 
	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false)
	public void testUpdateData(){
		System.out.println("start update!");
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
		commitStatistic.countStudentCommit();
	}
}
