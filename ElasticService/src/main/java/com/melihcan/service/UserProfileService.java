package com.melihcan.service;

import com.melihcan.repository.IUserProfileRepository;
import com.melihcan.repository.entity.UserProfile;
import com.melihcan.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String > {

    private final IUserProfileRepository userProfileRepository;

    public UserProfileService(IUserProfileRepository userProfileRepository){
        super(userProfileRepository);
        this.userProfileRepository=userProfileRepository;
    }
}
