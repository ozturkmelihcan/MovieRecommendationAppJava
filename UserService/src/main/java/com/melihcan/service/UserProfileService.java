package com.melihcan.service;

import com.melihcan.manager.AutManager;
import com.melihcan.dto.request.NewCreateUserRequestDto;
import com.melihcan.dto.request.UpdateRequestDto;
import com.melihcan.exception.ErrorType;
import com.melihcan.exception.UserManagerException;
import com.melihcan.mapper.IUserMapper;
import com.melihcan.repository.IUserProfileRepository;
import com.melihcan.repository.entity.UserProfile;
import com.melihcan.repository.enums.EStatus;
import com.melihcan.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final IUserProfileRepository userProfileRepository;
    private final AutManager authManager;
    public UserProfileService(IUserProfileRepository userProfileRepository,AutManager authManager){
        super(userProfileRepository);
        this.userProfileRepository=userProfileRepository;
        this.authManager=authManager;

    }

    public Boolean createUser(NewCreateUserRequestDto dto) {
        try {
            UserProfile userProfile = IUserMapper.INSTANCE.toUserProfile(dto);
            save(userProfile);
            return true;
        }catch (Exception e){
            throw new UserManagerException(ErrorType.USER_NOT_CREATED);
        }


    }


    public Boolean update(UpdateRequestDto dto) {

        Optional<UserProfile> userProfile=userProfileRepository.findOptionalByAuthId(dto.getAuthId());

        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }

        if (!dto.getEmail().equals(userProfile.get().getEmail())||!dto.getUsername().equals(userProfile.get().getUsername())){
            userProfile.get().setUsername(dto.getUsername());
            userProfile.get().setEmail(dto.getEmail());
            authManager.updateByUsernameOrEmail(IUserMapper.INSTANCE.toUpdateByEmailOrUserNameRequestDto(dto));
        }
        userProfile.get().setAbout(dto.getAbout());
        userProfile.get().setAddress(dto.getAddress());
        userProfile.get().setPhone(dto.getPhone());
        userProfile.get().setAvatar(dto.getAvatar());
        update(userProfile.get());
        return true;

    }

    public Boolean activateStatus(Long id) {
        Optional<UserProfile>userProfile = userProfileRepository.findOptionalByAuthId(id);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }
}
