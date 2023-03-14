package com.melihcan.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NewCreateUserRequestModel implements Serializable {

    private Long authId;
    private String username;
    private String email;
}
