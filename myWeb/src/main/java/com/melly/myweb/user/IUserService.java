package com.melly.myweb.user;

import com.melly.myweb.commons.inif.IServiceCRUD;
import com.melly.myweb.security.dto.LoginRequestDto;

public interface IUserService extends IServiceCRUD<IUser> {
    IUser login(LoginRequestDto loginRequestDto);

    Boolean changePassword(IUser dto) throws Exception;

    IUser findByLoginId(String loginId);
    IUser findByName(String name);
    IUser findByNickname(String nickname);
    IUser findByEmail(String email);

    int idCheck(String loginId);
    int nicknameCheck(String nickname);
    int emailCheck(String email);

}
