package com.melihcan.repository.entity;

import com.melihcan.repository.enums.ERole;
import com.melihcan.repository.enums.EStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
public class Auth extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String username;

    String email;

    String password;

    String activationCode;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    ERole role = ERole.USER;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    EStatus status = EStatus.PENDING;
}
