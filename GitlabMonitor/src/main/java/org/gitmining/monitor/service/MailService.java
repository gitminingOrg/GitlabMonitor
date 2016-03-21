package org.gitmining.monitor.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.mail.Address;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Service
public class MailService {
	@Autowired
	@Qualifier(value="mailSender")
	public JavaMailSender javaMailSender;
	@Autowired
	@Qualifier(value="mailMessage")
	public SimpleMailMessage simpleMailMessage;
	
	public boolean sendMail(String to, String title, String content){
		try{
			simpleMailMessage.setTo(to);
			simpleMailMessage.setText(content);
			simpleMailMessage.setSubject(title);
			javaMailSender.send(simpleMailMessage);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendHtmlMail(String to, String title, String content){
		try{
			MimeMessage mailMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
			helper.setFrom("ch2_27@sina.com");
			helper.setTo(to);
			helper.setSubject(title);
			helper.setText(content,true);
			javaMailSender.send(mailMessage);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendUpdateSuccessMail(){
		try{
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String today = sdf.format(calendar.getTime());
			simpleMailMessage.setText(today+" today no error, well done!");
			simpleMailMessage.setSubject("Today's gitlab data update succeed");
			javaMailSender.send(simpleMailMessage);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendUpdateSuccessMail(String day){
		try{
			Calendar calendar = Calendar.getInstance();
			simpleMailMessage.setText(day+" data no error, well done!");
			simpleMailMessage.setSubject(day+" gitlab data update succeed");
			javaMailSender.send(simpleMailMessage);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendUpdateFailureMail(){
		try{
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			String today = sdf.format(calendar.getTime());
			simpleMailMessage.setText(today+" today updating failed, try again!");
			simpleMailMessage.setSubject("Today's gitlab data update failed");
			javaMailSender.send(simpleMailMessage);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean sendUpdateFailureMail(String day){
		try{
			simpleMailMessage.setText(day+" data updating failed, try again!");
			simpleMailMessage.setSubject(day + " gitlab data update failed");
			javaMailSender.send(simpleMailMessage);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
