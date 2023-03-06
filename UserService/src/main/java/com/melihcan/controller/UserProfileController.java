package com.melihcan.controller;

import com.melihcan.dto.request.NewCreateUserRequestDto;
import com.melihcan.dto.request.UpdateRequestDto;
import com.melihcan.mapper.IUserMapper;
import com.melihcan.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Boolean> update(@RequestBody UpdateRequestDto dto){

        return ResponseEntity.ok(userProfileService.update(dto));
    }

    @PostMapping(ACTIVATESTATUS+"/{authId}")
    public ResponseEntity<Boolean>activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userProfileService.activateStatus(authId));
    }

}
