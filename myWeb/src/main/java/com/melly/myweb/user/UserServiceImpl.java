package com.melly.myweb.user;

import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.exception.IdNotFoundException;
import com.melly.myweb.security.dto.LoginRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserMybatisMapper userMybatisMapper;

    @Autowired
    private PasswordEncoder encoder;
    @Override
    public IUser login(LoginRequestDto loginRequestDto) {
        if(loginRequestDto == null || loginRequestDto.getLoginId() == null || loginRequestDto.getPassword() == null) {
            return null;
        }
        IUser user = this.userMybatisMapper.findByLoginId(loginRequestDto.getLoginId());
        if(user == null || !encoder.matches(loginRequestDto.getPassword(), user.getPassword())){
            return null;
        }
        return user;
    }

    @Override
    public Boolean changePassword(IUser user) throws Exception {
        if( user == null ){
            return false;
        }
        UserDto dto = new UserDto();
        dto.copyFields(user);
        dto.setPassword(encoder.encode(dto.getPassword()));
        this.userMybatisMapper.changePassword(dto);
        return true;
    }

    @Override
    public IUser findByLoginId(String loginId) {
        if ( loginId == null || loginId.isEmpty() ) {
            return null;
        }
        UserDto find = this.userMybatisMapper.findByLoginId(loginId);
        return find;
    }

    @Override
    public IUser findByName(String name) {
        if ( name == null || name.isEmpty() ) {
            return null;
        }
        UserDto find = this.userMybatisMapper.findByName(name);
        return find;
    }

    @Override
    public IUser findByNickname(String nickname) {
        if( nickname == null || nickname.isEmpty() ){
            return  null;
        }
        return userMybatisMapper.findByNickname(nickname);
    }

    @Override
    public IUser findByEmail(String email) {
        if ( email == null || email.isEmpty() ) {
            return null;
        }
        UserDto find = this.userMybatisMapper.findByEmail(email);
        return find;
    }

    @Override
    public int idCheck(String loginId) {
        int cnt = userMybatisMapper.idCheck(loginId);
        System.out.println("cnt: " + cnt);
        return cnt;
    }

    @Override
    public int emailCheck(String email) {
        int cnt = userMybatisMapper.emailCheck(email);
        System.out.println("cnt: " + cnt);
        return cnt;
    }

    @Override
    public int nicknameCheck(String nickname) {
        int cnt = userMybatisMapper.nicknameCheck(nickname);
        System.out.println("cnt: " + cnt);
        return cnt;
    }


    @Override
    public IUser insert(CUDInfoDto cudInfoDto, IUser user) {
        if( !this.isValidInsert(user) ){
            return null;
        }
        UserDto dto = UserDto.builder().build();
        dto.copyFields(user);
        dto.setPassword(encoder.encode(dto.getPassword()));
        this.userMybatisMapper.insert(dto);
        return dto;
    }

    private boolean isValidInsert(IUser dto){
        if(dto == null){
            return false;
        }
        else if(dto.getLoginId() == null && dto.getLoginId().isEmpty() ){
            return false;
        }
        else if(dto.getPassword() == null && dto.getPassword().isEmpty() ){
            return false;
        }
        else if(dto.getName() == null && dto.getName().isEmpty() ){
            return false;
        }
        else if(dto.getNickname() == null && dto.getNickname().isEmpty() ){
            return false;
        }
        else if(dto.getGender() == null && dto.getGender().isEmpty() ){
            return false;
        }
        else if(dto.getEmail() == null && dto.getEmail().isEmpty() ){
            return false;
        }
        return true;
    }
    @Override
    public IUser update(CUDInfoDto cudInfoDto, IUser user) {
        if( user == null || user.getId() == null || user.getId() <= 0 ){
            return null;
        }
        IUser find = this.findById(user.getId());
        find.copyFields(user);
        this.userMybatisMapper.update((UserDto) find);
        return find;
    }

    @Override
    public Boolean deleteById(Long id) {
        if( id == null || id <= 0 ) {
            return null;
        }
        this.userMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public IUser findById(Long id) {
        if(id == null || id <= 0){
            return null;
        }
        UserDto dto = this.userMybatisMapper.findById(id);
        if( dto == null ){
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        return dto;
    }
}
