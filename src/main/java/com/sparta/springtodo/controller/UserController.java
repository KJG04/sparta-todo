package com.sparta.springtodo.controller;

import com.sparta.springtodo.dto.CreateUserRequestDto;
import com.sparta.springtodo.dto.UserResponseDto;
import com.sparta.springtodo.entity.User;
import com.sparta.springtodo.mapper.UserMapper;
import com.sparta.springtodo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDto> create(@Validated @RequestBody CreateUserRequestDto userRequestDto) {
        User user = userService.createUser(userRequestDto.getName(), userRequestDto.getEmail());
        UserResponseDto userResponseDto = UserMapper.INSTANCE.toUserResponseDto(user);
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDto>> get() {
        List<User> users = userService.getUsers();
        List<UserResponseDto> userResponseDtoList = users.stream().map(UserMapper.INSTANCE::toUserResponseDto).toList();
        return ResponseEntity.ok(userResponseDtoList);
    }
}
