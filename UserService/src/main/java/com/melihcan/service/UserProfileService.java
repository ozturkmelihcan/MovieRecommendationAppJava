package com.melihcan.service;

import com.melihcan.dto.response.RoleResponseDto;
import com.melihcan.dto.response.UserFindAllResponseDto;
import com.melihcan.manager.AuthManager;
import com.melihcan.dto.request.NewCreateUserRequestDto;
import com.melihcan.dto.request.UpdateRequestDto;
import com.melihcan.exception.ErrorType;
import com.melihcan.exception.UserManagerException;
import com.melihcan.mapper.IUserMapper;
import com.melihcan.repository.IUserProfileRepository;
import com.melihcan.repository.entity.UserProfile;
import com.melihcan.repository.enums.EStatus;
import com.melihcan.utility.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final IUserProfileRepository userProfileRepositroy;

    private final AuthManager authManager;


    private final CacheManager cacheManager;

    public UserProfileService(IUserProfileRepository userProfileRepositroy, AuthManager authManager
                ,CacheManager cacheManager) {
        super(userProfileRepositroy);
        this.userProfileRepositroy = userProfileRepositroy;
        this.authManager = authManager;
        this.cacheManager=cacheManager;
    }

    public Boolean createUser(NewCreateUserRequestDto dto) {
        try {
            UserProfile userProfile= IUserMapper.INSTANCE.toUserProfile(dto);
            save(userProfile);
      //      cacheManager.getCache("myrole").clear();
            return  true;
        }catch (Exception e){
            throw  new UserManagerException(ErrorType.USER_NOT_CREATED);
        }

    }

    public Boolean update(UpdateRequestDto dto) {
        Optional<UserProfile> userProfile=userProfileRepositroy.findOptionalByAuthId(dto.getAuthId());

        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        cacheManager.getCache("myusername").evict(userProfile.get().getUsername().toLowerCase());
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
        Optional<UserProfile> userProfile=userProfileRepositroy.findOptionalByAuthId(id);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return  true;
    }

    @Cacheable(value = "username",key = "#username.toLowerCase()")
    public UserProfile findByUsername(String username){
        try {
            Thread.sleep(500);
        }catch (Exception e){
            e.printStackTrace();
        }
        Optional<UserProfile> userProfile=userProfileRepositroy.findOptionalByUsernameEqualsIgnoreCase(username);

        if (userProfile.isPresent()){
            return userProfile.get();
        }else {
            throw  new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
    }

    @Cacheable(value = "myrole",key = "#role.toLowerCase()")
    public List<UserProfile> findByRole(String role) {
        List<RoleResponseDto> list=authManager.findByRole(role).getBody();
        List <Optional<UserProfile>> users=list.stream().map(x-> userProfileRepositroy.findOptionalByAuthId(x.getId())).collect(Collectors.toList());
        return   users.stream().map(y->{
            if (y.isPresent()){
                return   y.get();
            }else{
                return  null;
            }
        }  ).collect(Collectors.toList());
    }

    public List<UserFindAllResponseDto> findAllUser() {
        return findAll().stream().map(x->IUserMapper.INSTANCE.toUserFindAllResponseDto(x)).collect(Collectors.toList());
    }
}

