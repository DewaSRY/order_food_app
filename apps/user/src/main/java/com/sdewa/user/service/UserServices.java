package com.sdewa.user.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sdewa.user.dto.UserDto;
import com.sdewa.user.entity.UserEntity;
import com.sdewa.user.mapstract.Mapstract;
import com.sdewa.user.repo.UserRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final UserRepo userRepo;

    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = Mapstract.INSTANCE.dtoToUser(userDto);
        UserEntity savedUser = userRepo.save(userEntity);
        return Mapstract.INSTANCE.userToDto(savedUser);
    }

    public ResponseEntity<UserDto> getUserById(Long id) {
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        UserDto userDto = Mapstract.INSTANCE.userToDto(userEntity);
        return ResponseEntity.ok(userDto);
    }
}
