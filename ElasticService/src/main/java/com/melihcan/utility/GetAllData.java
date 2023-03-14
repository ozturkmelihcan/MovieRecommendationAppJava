package com.melihcan.utility;


import com.melihcan.manager.IUserManager;
import com.melihcan.mapper.IUserMapper;
import com.melihcan.repository.entity.UserProfile;
import com.melihcan.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllData {

    private final UserProfileService userProfileService;

    private final IUserManager userManager;


//    @PostConstruct
//    public void initData() {
//        List<UserProfile> userProfileList = IUserMapper.INSTANCE.toUserProfiles(userManager.findAll().getBody());
//        userProfileService.saveAll(userProfileList);
//    }
}