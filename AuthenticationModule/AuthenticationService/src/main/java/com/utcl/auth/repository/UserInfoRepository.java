package com.utcl.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utcl.auth.entity.UserInfo;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    Optional<UserInfo> findByName(String username);

}
