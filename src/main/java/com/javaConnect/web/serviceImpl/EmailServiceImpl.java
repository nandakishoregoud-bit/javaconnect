package com.javaConnect.web.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.javaConnect.web.entity.User;
import com.javaConnect.web.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean sendEmail(User user) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(user.getEmail());
            helper.setSubject("Welcome to Java Connect 🚀");
            helper.setText(buildHtmlContent(user), true); // true = HTML mode

            javaMailSender.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String buildHtmlContent(User user) {
        return """
        <html>
        <body style="font-family: Arial, sans-serif; background-color: #f7f9fc; padding: 20px;">
            <div style="max-width: 600px; margin: auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); overflow: hidden;">
                <div style="background-color: #007bff; color: white; padding: 20px; text-align: center;">
                    <h2>Welcome to Java Connect 🚀</h2>
                </div>
                <div style="padding: 25px; color: #333;">
                    <p>Hi <strong>%s</strong>,</p>
                    <p>We’re thrilled to have you join <strong>Java Connect</strong> — a community built for developers and learners passionate about Java.</p>
                    <p>Here’s what you can do now:</p>
                    <ul>
                        <li>📘 Read structured notes on Java, OOPs, and Spring Boot</li>
                        <li>💡 Take interactive quizzes</li>
                        <li>💬 Ask and answer coding questions</li>
                    </ul>
                    <p>Start exploring Java the practical way today!</p>
                    <div style="text-align: center; margin-top: 20px;">
                        <a href="https://javaconnect.onrender.com" 
                           style="background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">
                           Visit Java Connect
                        </a>
                    </div>
                    <p style="margin-top: 25px;">Happy coding!<br>— The <strong>Java Connect</strong> Team 💻</p>
                </div>
                <div style="background-color: #f1f1f1; text-align: center; padding: 10px; font-size: 12px; color: #555;">
                    © 2025 Java Connect. All rights reserved.
                </div>
            </div>
        </body>
        </html>
        """.formatted(user.getUserName());
    }
}
