package com.melihcan.service;

import com.melihcan.rabbitmq.model.EmailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderService {

    private final JavaMailSender javaMailSender;

    public void sendMail(EmailModel model){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom("${mailusername}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("Aktivasyon kodunuz: ");
        mailMessage.setText(model.getActivationCode());
        javaMailSender.send(mailMessage);
    }

}
