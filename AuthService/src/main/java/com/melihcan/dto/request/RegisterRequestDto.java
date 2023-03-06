package com.melihcan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegisterRequestDto {

    @NotBlank
    @Size(min = 3,max = 20,message = "Kullanıcı adı en az 3 karakter en fazla 20 karakter olabilir.")
    String username;

    @NotBlank
    @Size(min = 8,max = 32,message = "Kullanıcı adı en az 8 karakter en fazla 32 karakter olabilir.")
    String password;

    @NotBlank
    @Email()
    String email;
}
