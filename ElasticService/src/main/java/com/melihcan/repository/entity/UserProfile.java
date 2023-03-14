package com.melihcan.repository.entity;


import com.melihcan.repository.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Document(indexName = "user_profile")
public class UserProfile extends BaseEntity {

    @Id
    private String id;

    private Long userId;
    private Long authId;
    private String username;
    private String email;
    private String phone;
    private String avatar;
    private String address;
    private String about;

    @Builder.Default
    private EStatus status=EStatus.PENDING;


}
