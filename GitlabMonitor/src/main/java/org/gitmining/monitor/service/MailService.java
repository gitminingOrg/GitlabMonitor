package org.gitmining.monitor.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.mail.internet.MimeMessage;

import org.gitmining.monitor.bean.MailBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
@Component
public class MailService {
	@Autowired
	@Qualifier(value="mailSender")
	private JavaMailSender javaMailSender;
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Autowired
	@Qualifier(value="mailMessage")
	private SimpleMailMessage simpleMailMessage;
	public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
		this.simpleMailMessage = simpleMailMessage;
	}
	
	@Autowired
	@Qualifier(value="jmsTemplate")
	private JmsTemplate jmsTemplate;
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * generating a sending email message, push it to activemq
	 * @param to
	 * @param title
	 * @param content
	 * @return
	 */
	public boolean sendMail(String to, String title, String content){
		final MailBean mailBean = new MailBean("ch2_27@sina.com",to,title,content,MailBean.SIMPLE_MAIL);
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(mailBean);
			}
		});
		return true;
	}
	
	/**
	 * generating a sending HTML email message, push it to activemq
	 * @param to
	 * @param title
	 * @param content
	 * @return
	 */
	public boolean sendHtmlMail(String to, String title, String content){
		final MailBean mailBean = new MailBean("ch2_27@sina.com",to,title,content,MailBean.HTML_MAIL);
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createObjectMessage(mailBean);
			}
		});
		return true;
	}
	
	
	public void handleMessage(MailBean mailBean){
		if(mailBean.getType()==MailBean.SIMPLE_MAIL){
			simpleMailMessage.setTo(mailBean.getTo());
			simpleMailMessage.setText(mailBean.getContent());
			simpleMailMessage.setSubject(mailBean.getTitle());
			javaMailSender.send(simpleMailMessage);
		}else if(mailBean.getType()==MailBean.HTML_MAIL){
			try{
				MimeMessage mailMessage = javaMailSender.createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
				helper.setFrom(mailBean.getFrom());
				helper.setTo(mailBean.getTo());
				helper.setSubject(mailBean.getTitle());
				helper.setText(mailBean.getContent(),true);
				javaMailSender.send(mailMessage);
			}catch(Exception e){
				e.printStackTrace();
			}
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
