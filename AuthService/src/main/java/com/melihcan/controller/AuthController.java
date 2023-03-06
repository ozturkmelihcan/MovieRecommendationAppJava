package com.melihcan.controller;

import com.melihcan.dto.request.ActivateRequestDto;
import com.melihcan.dto.request.LoginRequestDto;
import com.melihcan.dto.request.RegisterRequestDto;
import com.melihcan.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.melihcan.dto.response.LoginResponseDto;
import com.melihcan.dto.response.RegisterResponseDto;
import com.melihcan.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.melihcan.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){

        return ResponseEntity.ok(authService.register(dto));

    }

    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateRequestDto dto){

      return  ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PostMapping(ACTIVATESTATUS+"2")
    public ResponseEntity<Boolean> activateStatus2(@RequestBody ActivateRequestDto dto){

        return  ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<LoginResponseDto>login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PutMapping(UPDATEBYUSERNAMEOREMAIL)
    public ResponseEntity<Boolean>updateByUsernameOrEmail(@RequestBody UpdateByEmailOrUsernameRequestDto dto){
        return ResponseEntity.ok(authService.updateByUsernameOrEmail(dto));
    }

}
