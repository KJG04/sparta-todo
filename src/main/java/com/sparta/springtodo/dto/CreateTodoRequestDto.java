package com.sparta.springtodo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTodoRequestDto {
    @NotNull(message = "content는 null이 아니여야 합니다.")
    @Size(max = 200, message = "내용은 최대 200글자까지 작성 가능합니다.")
    @NotBlank(message = "비밀번호는 빈 값이 아니여야 합니다.")
    String content;

    @NotNull(message = "password는 null이 아니여야 합니다.")
    @NotBlank(message = "비밀번호는 빈 값이 아니여야 합니다.")
    String password;

    @NotNull(message = "userId는 null이 아니여야 합니다.")
    Long userId;
}
