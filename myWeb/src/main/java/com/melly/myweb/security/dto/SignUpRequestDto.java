package com.melly.myweb.security.dto;

import com.melly.myweb.user.IUser;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

public class SignUpRequestDto extends LoginRequestDto implements IUser {
    private Long id;

    @Size(min = 2, max = 30, message = "이름은 2~30 글자 사이로 하셔야 합니다.")
    private String name;

    @Size(min = 4, max = 20, message = "닉네임은 4~20 글자 사이로 하셔야 합니다.")
    private String nickname;

    @Size(min = 1, max = 100, message = "이메일은 1~100 글자 사이로 하셔야 합니다.")
    private String email;

    private String gender;
}
