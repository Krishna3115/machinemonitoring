package com.tblmonitoring.tblmonitor.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	 @Autowired
	    private JavaMailSender mailSender;


	 private final String[] managementEmails = {
			    "chaitanyatandale07@gmail.com",
			    "prashantkubal786@gmail.com",
			    "rnd@chakradharindustries.com"
			};

	    public void sendDeliveryStatusEmail(String subject, String body, MultipartFile attachment) throws MessagingException, IOException {
	        MimeMessage message = mailSender.createMimeMessage();

	        MimeMessageHelper helper = new MimeMessageHelper(message, attachment != null);

	        helper.setFrom("chaitanyatandale05@gmail.com"); // must match spring.mail.username
	        helper.setTo(managementEmails);
	        helper.setSubject(subject);
	        helper.setText(body);

	        if (attachment != null && !attachment.isEmpty()) {
	            helper.addAttachment("ReceivingLetter.pdf", attachment);
	        }

	        mailSender.send(message);
	    }
}
