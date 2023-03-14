package com.melihcan.rabbitmq.producer;

import com.melihcan.rabbitmq.model.EmailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {
    @Value("${rabbitmq.exchange-auth}")
    private String directExchange;
    @Value("${rabbitmq.emailKey}")
    private String emailBindingKey;

    private  final RabbitTemplate rabbitTemplate;

    public void sendActivationCode(EmailModel model){
        rabbitTemplate.convertAndSend(directExchange,emailBindingKey,model);
    }
}