package com.melihcan.manager;

import com.melihcan.dto.request.UpdateByEmailOrUserNameRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.melihcan.constant.ApiUrls.ACTIVATESTATUS;
import static com.melihcan.constant.ApiUrls.UPDATEBYUSERNAMEOREMAIL;

@FeignClient(name = "auth-user",decode404 = true,url = "http://localhost:8090/api/v1/auth")
public interface AutManager {
    @PutMapping(UPDATEBYUSERNAMEOREMAIL)
    public ResponseEntity<Boolean> updateByUsernameOrEmail(@RequestBody UpdateByEmailOrUserNameRequestDto dto);
    @PostMapping(ACTIVATESTATUS+"/{authId}")
    ResponseEntity<Boolean>activateStatus(@PathVariable Long authId);
}
