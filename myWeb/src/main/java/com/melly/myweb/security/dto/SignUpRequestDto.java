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
    private String name;
    private String nickname;
    private String email;
    private String gender;
}
