package com.javaConnect.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.javaConnect.web.entity.User;
import com.javaConnect.web.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public boolean sendEmail(User user) {
		
		String subject = "Welcome to Java Connect 🚀";
	    String body = "Hello " + user.getUserName() + ",\n\n"
	            + "Welcome to Java Connect! 🎉\n"
	            + "We’re excited to have you as part of our developer community.\n\n"
	            + "You can now log in and start exploring Java tutorials, quizzes, and discussions.\n\n"
	            + "Happy coding!\n"
	            + "— The Java Connect Team";
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(user.getEmail());
		message.setSubject(subject);
		message.setText(body);
		try {
			javaMailSender.send(message);
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
