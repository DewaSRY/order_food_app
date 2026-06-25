package com.sdewa.auth.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sdewa.auth.entity.UserEntity;

public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

}
