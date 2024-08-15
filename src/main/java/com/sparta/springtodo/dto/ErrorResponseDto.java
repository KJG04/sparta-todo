package com.sparta.springtodo.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponseDto {
    List<String> messages;
    Integer status;
    LocalDateTime timestamp;
    String error;
    String path;
}
