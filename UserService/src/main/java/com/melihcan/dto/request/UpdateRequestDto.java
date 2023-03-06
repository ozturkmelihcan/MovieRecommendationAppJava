package com.melihcan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateRequestDto {

    Long authId;
    String username;

    String email;

    String phone;

    String avatar;

    String address;

    String about;
}
