package com.sparta.springtodo.dto;

import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTodoRequestDto {
    @Nullable
    String content;
    
    @Nullable
    Long userId;
}
