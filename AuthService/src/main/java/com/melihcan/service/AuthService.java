package com.melihcan.service;

import com.melihcan.dto.request.ActivateRequestDto;
import com.melihcan.dto.request.LoginRequestDto;
import com.melihcan.dto.request.RegisterRequestDto;
import com.melihcan.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.melihcan.dto.response.RegisterResponseDto;
import com.melihcan.dto.response.RoleResponseDto;
import com.melihcan.exception.AuthManagerException;
import com.melihcan.exception.ErrorType;
import com.melihcan.manager.IUserManager;
import com.melihcan.mapper.IAuthMapper;
import com.melihcan.rabbitmq.model.EmailModel;
import com.melihcan.rabbitmq.producer.EmailProducer;
import com.melihcan.rabbitmq.producer.RegisterUserProducer;
import com.melihcan.repository.IAuthRepository;
import com.melihcan.repository.entity.Auth;
import com.melihcan.repository.enums.ERole;
import com.melihcan.repository.enums.EStatus;
import com.melihcan.utility.CodeGenerator;
import com.melihcan.utility.JwtTokenManager;
import com.melihcan.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;
    private final IUserManager userManager;
    private final JwtTokenManager jwtTokenManager;

    private final RegisterUserProducer userProducer;

    private final CacheManager cacheManager;

    private final EmailProducer emailProducer;
    public AuthService(IAuthRepository authRepository, IUserManager userManager, JwtTokenManager jwtTokenManager,
                       RegisterUserProducer userProducer,CacheManager cacheManager, EmailProducer emailProducer) {
        super(authRepository);
        this.authRepository = authRepository;
        this.userManager = userManager;
        this.jwtTokenManager = jwtTokenManager;
        this.userProducer = userProducer;
        this.emailProducer = emailProducer;
        this.cacheManager=cacheManager;
    }

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto dto) {
        try {
            Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
            auth.setActivationCode(CodeGenerator.generateCode());
            save(auth);
            userManager.createUser(IAuthMapper.INSTANCE.toNewCreateUserRequestDto(auth));
            return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        }catch (Exception e){
            throw new AuthManagerException(ErrorType.USER_NOT_CREATED);
        }
    }
    @Transactional
    public RegisterResponseDto registerWithRabbitMq(RegisterRequestDto dto) {
        try {
            Auth auth= IAuthMapper.INSTANCE.toAuth(dto);
            auth.setActivationCode(CodeGenerator.generateCode());
            save(auth);
            //rabbit mq ile haberleşme sağlayacağım
            userProducer.sendNewUser(IAuthMapper.INSTANCE.toNewCreateUserRequestModel(auth));
            emailProducer.sendActivationCode(EmailModel.builder().email(auth.getEmail()).activationCode(auth.getActivationCode()).build());
            cacheManager.getCache("myrole").evict(auth.getRole().toString().toLowerCase());
            return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);
        }catch (Exception e){
            throw new AuthManagerException(ErrorType.USER_NOT_CREATED);
        }
    }

    public Boolean activateStatus(ActivateRequestDto dto) {
        Optional<Auth> auth=findById(dto.getId());
        if (auth.isEmpty()){
            throw  new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (dto.getActivationCode().equals(auth.get().getActivationCode())){
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            userManager.activateStatus(dto.getId());
            return  true;
        }else{
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public Boolean activateStatus2(ActivateRequestDto dto) {
        Optional<Auth> auth=findById(dto.getId());
        if (auth.isEmpty()){
            throw  new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (dto.getActivationCode().equals(auth.get().getActivationCode())){
            auth.get().setStatus(EStatus.ACTIVE);
            save(auth.get());
            userManager.activateStatus2(dto.getId());
            return  true;
        }else{
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public String login(LoginRequestDto dto) {
        Optional<Auth> auth=authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()){
            throw  new AuthManagerException(ErrorType.LOGIN_ERROR);
        }
        if(!auth.get().getStatus().equals(EStatus.ACTIVE)){
            throw  new AuthManagerException(ErrorType.LOGIN_ERROR_STATUS);
        }
        Optional<String> token=jwtTokenManager.createToken(auth.get().getId(),auth.get().getRole());
        if (token.isEmpty()){
            throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        }
        return token.get();
    }

    public Boolean updateByUsernaemOrEmail(UpdateByEmailOrUsernameRequestDto dto) {
        Optional<Auth> auth=authRepository.findById(dto.getId());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setEmail(dto.getEmail());
        auth.get().setUsername(dto.getUsername());
        update(auth.get());
        return true;
    }

    public List<RoleResponseDto> findByRole(String role) {
        ERole myrole;
        try {
            myrole = ERole.valueOf(role.toUpperCase());
        } catch (Exception exception) {
            throw new AuthManagerException(ErrorType.ROLE_NOT_FOUND);
        }
        //  authRepository.findAllByRole(myrole).stream().map(x->IAuthMapper.INSTANCE.toRoleResponseDto(x)).collect(Collectors.toList());
        return IAuthMapper.INSTANCE.toRoleResponseDtoList(authRepository.findAllByRole(myrole));
    }
}
