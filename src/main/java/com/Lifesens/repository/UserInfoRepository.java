package com.Lifesens.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Lifesens.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
}
