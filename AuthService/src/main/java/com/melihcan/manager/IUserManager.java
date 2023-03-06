package com.melihcan.manager;

import com.melihcan.dto.request.NewCreateUserRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.melihcan.constant.ApiUrls.ACTIVATESTATUS;

@FeignClient(name = "user-userprofile",decode404 = true, url = "http://localhost:8091/api/v1/user")
public interface IUserManager {

    @PostMapping("create")
    ResponseEntity<Boolean>createUser(@RequestBody NewCreateUserRequestDto dto);

    @PostMapping(ACTIVATESTATUS+"/{authId}")
    ResponseEntity<Boolean>activateStatus(@PathVariable Long authId);

}
