package com.sparta.springtodo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserRequestDto {
    @NotNull
    @NotBlank
    private String name;

    @NotNull(message = "email은 null이 아니여야 합니다.")
    @Email(message = "email은 이메일 형식이여야 합니다.")
    @NotBlank(message = "email은 빈 값이 아니여야 합니다.")
    private String email;
}
