package com.Lifesens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Lifesens.entity.UserLogin;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Integer> {
    Optional<UserLogin> findByUsername(String username);
}
