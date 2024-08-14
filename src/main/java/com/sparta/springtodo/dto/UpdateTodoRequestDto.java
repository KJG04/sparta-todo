package com.sparta.springtodo.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTodoRequestDto {
    String content;
    Long userId;
}
