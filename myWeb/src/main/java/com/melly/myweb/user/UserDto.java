package com.melly.myweb.user;

import com.melly.myweb.commons.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDto extends BaseDto implements IUser {
    Long id;
    String loginId;
    String password;
    String name;
    String nickname;
    String gender;
    String email;
    String photo;

}
