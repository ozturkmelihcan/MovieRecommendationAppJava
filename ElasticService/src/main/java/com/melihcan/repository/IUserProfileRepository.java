package com.melihcan.repository;

import com.melihcan.repository.entity.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserProfileRepository extends ElasticsearchRepository<UserProfile,String > {


    Optional<UserProfile> findOptionalByAuthId(Long id);
    Optional<UserProfile> findOptionalByUsernameEqualsIgnoreCase(String username);

}
