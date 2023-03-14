package com.melihcan.controller;

import com.melihcan.manager.IUserManager;
import com.melihcan.repository.entity.UserProfile;
import com.melihcan.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elasticuser")
public class UserProfileController {

    private final UserProfileService userProfileService;
    private final IUserManager userManager;

    @GetMapping("/findall")
    public ResponseEntity<Iterable<UserProfile>> findAll(){
     //   userManager.findAll().getBody().forEach(System.out::println);
        return  ResponseEntity.ok(userProfileService.findAll());
    }
}
