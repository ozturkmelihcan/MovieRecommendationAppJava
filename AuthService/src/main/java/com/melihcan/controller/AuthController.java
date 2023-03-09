package com.melihcan.controller;

import com.melihcan.dto.request.ActivateRequestDto;
import com.melihcan.dto.request.LoginRequestDto;
import com.melihcan.dto.request.RegisterRequestDto;
import com.melihcan.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.melihcan.dto.response.RegisterResponseDto;
import com.melihcan.service.AuthService;
import com.melihcan.utility.JwtTokenManager;
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

    private final JwtTokenManager jwtTokenManager;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){

        return ResponseEntity.ok(authService.register(dto));

    }

    @PostMapping(REGISTER+"2")
    public ResponseEntity<RegisterResponseDto> register2(@RequestBody @Valid RegisterRequestDto dto){

        return ResponseEntity.ok(authService.registerWithRabbitMQ(dto));

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
    public ResponseEntity<String >login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PutMapping(UPDATEBYUSERNAMEOREMAIL)
    public ResponseEntity<Boolean>updateByUsernameOrEmail(@RequestBody UpdateByEmailOrUsernameRequestDto dto){
        return ResponseEntity.ok(authService.updateByUsernameOrEmail(dto));
    }

    @GetMapping("/getrolefromtoken")
    public ResponseEntity<String >getRoleFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getRoleFromToken(token).get());
    }

    @GetMapping("/getidfromtoken")
    public ResponseEntity<Long >getIdFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getIdFromToken(token).get());
    }
}
