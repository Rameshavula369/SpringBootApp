package com.effort.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;

import com.effort.dao.ExtraDao;
import com.effort.entity.Mail;

import org.springframework.stereotype.Service;


@Service
public class MailTask {

	
	@Autowired
	private ExtraDao extraDao; 
	
	public long sendMail(String from, String to, String subject, String msg, int bodyType, String companyId, boolean verificationRequired,int mailSentType) 
			throws MessagingException {
		if(bodyType==2 && mailSentType != 2 && mailSentType != 4 && mailSentType != 5)
		{
			if(!msg.startsWith("<PRE>"))
			{
				msg = "<PRE>"+msg+"</PRE>";
			}
		}
		Mail mail = new Mail();
		mail.setMailFrom(from);
		mail.setMailTo(to);
		mail.setMailSubject(subject);
		mail.setMailBody(msg);
	    mail.setMailBodyType(bodyType);
	    mail.setCompanyId(companyId);
	    mail.setVerificationRequired(verificationRequired);
	    mail.setMailSentType(mailSentType);
	    
		long id = extraDao.insertMail(mail);
		return id;
	}
	public void sendHighPriorityMail(String from, String to, String subject, String mailBody, int bodyType, String companyId, 
			boolean verificationRequired,int priority,int mailSentType) 
			throws MessagingException {
		Mail mail = new Mail();
		mail.setMailFrom(from);
		mail.setMailTo(to);
		mail.setMailSubject(subject);
		mail.setMailBody(mailBody);
	    mail.setMailBodyType(bodyType);
	    mail.setCompanyId(companyId);
	    mail.setVerificationRequired(verificationRequired);
	    mail.setPriority(priority);
	    mail.setMailSentType(mailSentType);
		extraDao.insertMail(mail);
	}
	
	
}
