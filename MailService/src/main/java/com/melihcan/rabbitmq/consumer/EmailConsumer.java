package com.melihcan.rabbitmq.consumer;

import com.melihcan.rabbitmq.model.EmailModel;
import com.melihcan.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {


    private final MailSenderService mailSenderService;

    @RabbitListener(queues ="${rabbitmq.queueEmail}" )
    public void activationCode(EmailModel model){
        log.info("Model {} ",model.toString());
        mailSenderService.sendMail(model);
    }
}
