package com.sdewa.user.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sdewa.user.entity.UserEntity;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Long> {

}
