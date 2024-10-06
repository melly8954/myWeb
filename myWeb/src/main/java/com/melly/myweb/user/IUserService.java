package com.melly.myweb.user;

import com.melly.myweb.commons.inif.IServiceCRUD;
import com.melly.myweb.security.dto.LoginRequest;

public interface IUserService extends IServiceCRUD<IUser> {
    IUser login(LoginRequest loginRequest);

    Boolean changePassword(IUser dto) throws Exception;

    IUser findByLogin(String login);
    IUser findByName(String name);
    IUser findByNickname(String nickname);
    IUser findByEmail(String email);

    int idCheck(String loginId);
    int nicknameCheck(String nickname);
    int emailCheck(String email);

}
