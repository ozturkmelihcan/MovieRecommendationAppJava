package com.melihcan.dto.response;

import com.melihcan.repository.enums.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponseDto {

    private Long id;

    private String username;

    private String email;

    private ERole role;


}
