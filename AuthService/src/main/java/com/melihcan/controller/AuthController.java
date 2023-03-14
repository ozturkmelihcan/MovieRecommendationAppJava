package com.melihcan.controller;

import com.melihcan.dto.request.ActivateRequestDto;
import com.melihcan.dto.request.LoginRequestDto;
import com.melihcan.dto.request.RegisterRequestDto;
import com.melihcan.dto.request.UpdateByEmailOrUsernameRequestDto;
import com.melihcan.dto.response.RegisterResponseDto;
import com.melihcan.dto.response.RoleResponseDto;
import com.melihcan.repository.entity.Auth;
import com.melihcan.service.AuthService;
import com.melihcan.utility.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.melihcan.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(AUTH)
public class AuthController {

    private final AuthService authService;

    private final JwtTokenManager jwtTokenManager;

    private final CacheManager cacheManager;


    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(REGISTER+"2")
    public ResponseEntity<RegisterResponseDto> register2(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.registerWithRabbitMq(dto));
    }
    @PostMapping(ACTIVATESTATUS)
    public  ResponseEntity<Boolean> activateStatus(@RequestBody ActivateRequestDto dto){
        return   ResponseEntity.ok(authService.activateStatus(dto));
    }
    @PostMapping(ACTIVATESTATUS+"2") //activatestatus2
    public  ResponseEntity<Boolean> activateStatus2(@RequestBody ActivateRequestDto dto){
        return   ResponseEntity.ok(authService.activateStatus2(dto));
    }
    @PostMapping(LOGIN)
    public  ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PutMapping(UPDATEBYUSERNAMEOREMAIL)
    public ResponseEntity<Boolean> updateByUsernameOrEmail(@RequestBody UpdateByEmailOrUsernameRequestDto dto){

        return ResponseEntity.ok(authService.updateByUsernaemOrEmail(dto));

    }

    @GetMapping("/getRoleFromToken")
    public  ResponseEntity<String> getRoleFromToken(String token){

        return ResponseEntity.ok(jwtTokenManager.getRoleFromToken(token).get());


    }

    @GetMapping("/getIdFromToken")
    public  ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getIdFromToken(token).get());
    }

    @GetMapping("/redis")
    @Cacheable(value = "redisexample")
    public  String redisExample(String value){
        try {//
            Thread.sleep(2000);

            return  value;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/redisdelete")
    @CacheEvict(value = "redisexample",allEntries = true)
    public void redisDeleteExample(){

    }

    @GetMapping("/redisdelete2")
    public boolean redisDeleteExample2(){
        try {
            //  cacheManager.getCache("redisexample").clear();// aynı isimli cahcde butun değerleri siler;
            cacheManager.getCache("redisexample").evict("Mustafa");
            return  true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return  false;
        }
    }

    @GetMapping("findbyrole/{role}")
    public ResponseEntity<List<RoleResponseDto>>findByRole(@PathVariable String role){
        return ResponseEntity.ok(authService.findByRole(role));
    }
}
