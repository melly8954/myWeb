package com.melly.myweb.user;

import com.melly.myweb.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IUserMybatisMapper extends IMybatisCRUD<UserDto> {
    UserDto findByLoginId(String loginId);
    UserDto findByName(String name);
    UserDto findByNickname(String nickname);
    UserDto findByEmail(String email);

    void changePassword(UserDto dto);

    int idCheck(String loginId);
    int emailCheck(String email);
    int nicknameCheck(String nickname);

}
