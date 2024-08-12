package com.sparta.springtodo.controller;

import com.sparta.springtodo.dto.UserRequestDto;
import com.sparta.springtodo.dto.UserResponseDto;
import com.sparta.springtodo.entity.User;
import com.sparta.springtodo.service.UserService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserResponseDto> create(@RequestBody UserRequestDto userRequestDto) {
        User user = userService.createUser(userRequestDto.getName(), userRequestDto.getEmail());
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .build();
        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<List<UserResponseDto>> get() {
        List<User> users = userService.getUsers();
        List<UserResponseDto> userResponseDtos = users.stream().map(v -> UserResponseDto.builder()
                .id(v.getId())
                .name(v.getName())
                .email(v.getEmail())
                .createAt(v.getCreateAt())
                .updateAt(v.getUpdateAt())
                .build()).toList();
        return ResponseEntity.ok(userResponseDtos);
    }
}
