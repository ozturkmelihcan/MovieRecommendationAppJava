package com.melihcan.rabbitmq.consumer;

import com.melihcan.mapper.IUserMapper;
import com.melihcan.rabbitmq.model.NewCreateUserRequestModel;
import com.melihcan.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NewUserConsumer {


    private final UserProfileService userProfileService;

    @RabbitListener(queues = ("${rabbitmq.queueRegister}"))
    public  void newUserCreate(NewCreateUserRequestModel model){
        log.info("User {}",model.toString());
        userProfileService.createUser(IUserMapper.INSTANCE.toNewCreateUserRequestDto(model));
    }


}
