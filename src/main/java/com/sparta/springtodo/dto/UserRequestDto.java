package com.sparta.springtodo.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserRequestDto {
    private String name;
    private String email;
}
