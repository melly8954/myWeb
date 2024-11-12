package com.melly.myweb.security.dto;

import com.melly.myweb.commons.dto.BaseNullRequest;
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
public class LoginRequestDto extends BaseNullRequest {
    @Size(min = 5, max = 20, message = "로그인Id는 5~20자 사이여야 합니다.")
    private String loginId;

    @Size(min = 5, max = 20, message = "로그인Pw는 5~20자 사이여야 합니다.")
    private String password;

}
