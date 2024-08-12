package com.sparta.springtodo.mapper;

import com.sparta.springtodo.dto.UserResponseDto;
import com.sparta.springtodo.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserResponseDto toUserResponseDto(User user);
}
