package com.melly.myweb.user;

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
public class UserDto implements IUser {
    Long id;
    String loginId;
    String password;
    String name;
    String nickname;
    String gender;
    String email;
    String photo;

}
