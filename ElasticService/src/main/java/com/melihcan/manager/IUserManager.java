package com.melihcan.manager;

import com.melihcan.dto.response.UserFindAllResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service-elastic-userprofile",url = "http://localhost:8091/api/v1/user",decode404 = true)
public interface IUserManager {
    @GetMapping("/findall")
    ResponseEntity<List<UserFindAllResponseDto>> findAll();
}
