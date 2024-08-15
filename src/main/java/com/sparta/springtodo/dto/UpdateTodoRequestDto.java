package com.sparta.springtodo.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTodoRequestDto {
    @Size(max = 200, message = "content는 최대 200글자까지 작성 가능합니다.")
    String content;

    Long userId;
}
