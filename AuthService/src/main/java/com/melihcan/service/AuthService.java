package com.melihcan.service;

import com.melihcan.dto.request.ActivateRequestDto;
import com.melihcan.dto.request.LoginRequestDto;
import com.melihcan.dto.request.RegisterRequestDto;
import com.melihcan.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.melihcan.dto.response.LoginResponseDto;
import com.melihcan.dto.response.RegisterResponseDto;
import com.melihcan.exception.AuthManagerException;
import com.melihcan.exception.ErrorType;
import com.melihcan.manager.IUserManager;
import com.melihcan.mapper.IAuthMapper;
import com.melihcan.repository.IAuthRepository;
import com.melihcan.repository.entity.Auth;
import com.melihcan.repository.enums.EStatus;
import com.melihcan.utility.CodeGenerator;
import com.melihcan.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;

    private final IUserManager userManager;

    public AuthService(IAuthRepository authRepository,IUserManager userManager){
        super(authRepository);
        this.authRepository=authRepository;
        this.userManager=userManager;
    }

    public RegisterResponseDto register(RegisterRequestDto dto) {
        try {
            Auth auth = IAuthMapper.INSTANCE.toAuth(dto);           // save'leme işlemi yapınca buradaki auth id mi almıs olacagım. otomatik olarak atanmış olacak.
            auth.setActivationCode(CodeGenerator.generateCode());
            save(auth);
            userManager.createUser(IAuthMapper.INSTANCE.toNewCreateUserRequestDto(auth));
            return IAuthMapper.INSTANCE.toRegisterResponseDto(auth);        // bu kısımda id setlenmiş olacak.
        }catch (Exception exception){
            throw new AuthManagerException(ErrorType.USER_NOT_CREATED);  // Error mesaj düzenlenecek.
        }

    }

    public Boolean activateStatus(ActivateRequestDto dto) {
        Optional<Auth>auth = findById(dto.getId());     // id'ye eriştik.
        if (auth.isEmpty()){                            // boşsa exception fırlatacak.
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        if (dto.getActivationCode().equals(auth.get().getActivationCode())){        // dtodan gelen code ile auth'daki code eşitse
            auth.get().setStatus(EStatus.ACTIVE);                                   // status'ü setliyoruz ve auth'u kaydediyoruz
            save(auth.get());


            return true;                                                            // true döndürüp sonlandırıyoruz.
        }else {
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Optional<Auth>auth = authRepository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        }
        if (auth.get().getStatus().equals(EStatus.ACTIVE)){
            throw new AuthManagerException(ErrorType.LOGIN_ERROR_STATUS);
        }
        return IAuthMapper.INSTANCE.toLoginResponseDto(auth.get());

    }

    public Boolean updateByUsernameOrEmail(UpdateByEmailOrUsernameRequestDto dto) {
        Optional<Auth> auth=authRepository.findById(dto.getId());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        auth.get().setEmail(dto.getEmail());
        auth.get().setUsername(dto.getUsername());
        update(auth.get());
        return true;
    }
}
