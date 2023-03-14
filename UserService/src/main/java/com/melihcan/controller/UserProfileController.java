package com.melihcan.controller;

import com.melihcan.dto.request.NewCreateUserRequestDto;
import com.melihcan.dto.request.UpdateRequestDto;
import com.melihcan.dto.response.UserFindAllResponseDto;
import com.melihcan.mapper.IUserMapper;
import com.melihcan.repository.entity.UserProfile;
import com.melihcan.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.melihcan.constant.ApiUrls.*;
@RestController
@RequiredArgsConstructor
@RequestMapping(USER)
public class UserProfileController {


    private final UserProfileService userProfileService;


    @PostMapping(CREATE)
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserRequestDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Boolean> update(@RequestBody @Valid UpdateRequestDto dto){

        return ResponseEntity.ok(userProfileService.update(dto));
    }

    @PostMapping(ACTIVATESTATUS+"/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable  Long authId){
        return   ResponseEntity.ok(userProfileService.activateStatus(authId));
        //  ....... activatestatsus/1
    }
    @PostMapping(ACTIVATESTATUS)
    public ResponseEntity<Boolean> activateStatus2(@RequestParam Long authId){
        return   ResponseEntity.ok(userProfileService.activateStatus(authId));
        // .......activatestatsus?authId=1
    }

    @GetMapping(FINDALL)
    public  ResponseEntity<List<UserFindAllResponseDto>> findAll(){
        return  ResponseEntity.ok(userProfileService.findAllUser());
    }


    @GetMapping(FINDBYUSERNAME) ///findbyusername/{username}"
    public ResponseEntity<UserProfile> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }

    @GetMapping("/findbyrole/{role}")
    public ResponseEntity<List<UserProfile>> findByRole(@PathVariable String role){
        return ResponseEntity.ok(userProfileService.findByRole(role));
    }



}
