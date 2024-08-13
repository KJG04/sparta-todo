package com.sparta.springtodo.dto;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTodoRequestDto {
    String content;
    String password;
    Long userId;
}
