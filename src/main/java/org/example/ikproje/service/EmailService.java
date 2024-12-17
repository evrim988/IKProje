package org.example.ikproje.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSenderImpl mailSender;

	
	public void sendEmail(String alici, String token)  {
		String url  = "http://localhost:9090/v1/dev/user/verify-account?token=" + token;
		String body = "Hesabınızı onaylamak için lütfen aşağıdaki linke tıklayınız. \n\n"+url;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(alici);
		message.setSubject("Mail Onayı");
		message.setText(body);
		mailSender.send(message);
	}

	public void sendResetPasswordEmail(String email, String token)  {
		String url = "http://localhost:9090/v1/dev/user/reset-password?token=" + token;
		String body = "Şifrenizi değiştirmek için lütfen aşağıdaki linke tıklayınız.\n"+url;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Şifre sıfırlama isteğiniz hk.");
		message.setText(body);
		mailSender.send(message);
	}


}