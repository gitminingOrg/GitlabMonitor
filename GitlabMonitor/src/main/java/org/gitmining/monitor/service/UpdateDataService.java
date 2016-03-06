package org.gitmining.monitor.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.gitmining.monitor.bean.ProjectEvent;
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

	@Transactional(propagation=Propagation.REQUIRES_NEW, readOnly=false,timeout=5)
	public void testUpdateData(){
		System.out.println("service start!");
		ProjectEvent projectEvent = new ProjectEvent();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		for (int i = 0; i < 10; i++) {
			
			calendar.add(Calendar.DATE, i);
			Random random = new Random();
			projectEvent.setComment(random.nextInt(10));
			projectEvent.setCreate(random.nextInt(10));
			projectEvent.setDay(sdf.format(calendar.getTime()));
			projectEvent.setIssue(random.nextInt(10));
			projectEvent.setPush(random.nextInt(10));
			projectEvent.setTeam("testTeam3");
			projectEvent.setTotal(random.nextInt(10));
			projectDao.insertProjectEvent(projectEvent);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
