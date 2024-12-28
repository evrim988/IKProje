package org.example.ikproje.service;

import lombok.RequiredArgsConstructor;
import org.example.ikproje.entity.Leave;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSenderImpl mailSender;

	
	public void sendEmail(String alici, String token)  {
		String url  = "http://localhost:9090/v1/dev/auth/verify-account?token=" + token;
		String body = "Hesabınızı onaylamak için lütfen aşağıdaki linke tıklayınız. \n\n"+url;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(alici);
		message.setSubject("Mail Onayı");
		message.setText(body);
		mailSender.send(message);
	}

	public void sendResetPasswordEmail(String email, String token)  {
		String url = "http://localhost:3000/resetPassword?token=" + token;
		String body = "Şifrenizi değiştirmek için lütfen aşağıdaki linke tıklayınız.\n"+url;
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(email);
		message.setSubject("Şifre sıfırlama isteğiniz hk.");
		message.setText(body);
		mailSender.send(message);
	}
	
	public void sendAdminConfirmationEmail(String email,String message)  {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("About register");
		msg.setText(message);
		mailSender.send(msg);
	}

	public void sendPersonelActivationConfirmationEmail(String email)  {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("Şirket hesabı aktifleştirme hk.");
		msg.setText("Şirket hesabınız aktifleştirilmiştir. Tanımlanan mail ve şifreniz ile giriş yapabilirsiniz.");
		mailSender.send(msg);
	}

	public void sendApprovedLeaveNotificationEmail(String email, Leave leave){
		String approvedMessage = "Yapmış olduğunuz izin başvurusu yönetici tarafından onaylanmıştır. İyi tatiller dileriz\n" +
				"İzin başlangıç tarihi: "+leave.getStartDate()+"\n"+
				"İzin bitiş tarihi    : "+leave.getEndDate()+"\n"+
				"İzin türü            :"+leave.getLeaveType();
		
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("İzin başvurusu hk.");
		msg.setText(approvedMessage);
		mailSender.send(msg);
	}
	
	public void sendRejectedLeaveNotificationEmail(String email, Leave leave, String message){
		String rejectedMessage = "Yapmış olduğunuz izin başvurusu reddedilmiştir. İyi günler dileriz.\n"+
				"İzin açıklaması: "+message;
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("İzin başvurusu hk.");
		msg.setText(rejectedMessage);
		mailSender.send(msg);
	}

	public void sendAssetAssignmentRejectionMessage(String emailTo,String emailFrom, String message){
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(emailTo);
		msg.setFrom(emailFrom);
		msg.setSubject("Zimmet ataması hk.");
		msg.setText(message);
		mailSender.send(msg);
	}


}