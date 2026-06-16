package com.sdewa.user.mapstract;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.sdewa.user.dto.UserDto;
import com.sdewa.user.entity.UserEntity;

@Mapper
public interface Mapstract {

    Mapstract INSTANCE = Mappers.getMapper(Mapstract.class);

    UserDto userToDto(UserEntity userEntity);
    UserEntity dtoToUser(UserDto userDto);
    
}
