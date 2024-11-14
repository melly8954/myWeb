package com.melly.myweb.security.controller;

import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.ResponseCode;
import com.melly.myweb.commons.dto.ResponseDto;
import com.melly.myweb.commons.inif.ICommonRestController;
import com.melly.myweb.security.config.SecurityConfig;
import com.melly.myweb.security.dto.*;
import com.melly.myweb.user.IUser;
import com.melly.myweb.user.IUserService;
import com.melly.myweb.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("api/v1/login")
public class LoginRestController implements ICommonRestController{

    @Autowired
    IUserService userService;

    @PostMapping("/signin")
    private ResponseEntity<ResponseDto> signin(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request){
        try{
            if(loginRequestDto == null){
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051,"입력 매개변수 에러", null);
            }
            IUser loginUser = this.userService.login(loginRequestDto);
            if(loginUser == null){
                return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R000074,"로그인 실패 했습니다. ID와 암호를 확인하세요", null);
            }
            HttpSession session = request.getSession();
            session.setAttribute(SecurityConfig.LOGINUSER,loginUser.getNickname());
            session.setMaxInactiveInterval(60*60); // 세션별 유효시간 지정
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공",true);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.toString(), null);
        }
    }

    @PostMapping("findid")
    private ResponseEntity<FindIdResponseDto> findId(@Validated @RequestBody FindIdDto findIdDto){
        FindIdResponseDto responseDto = new FindIdResponseDto();
        try{
            if(findIdDto.getName().isEmpty() || findIdDto.getEmail().isEmpty()){
                responseDto.setMessage("name과 email을 입력하세요");
                return ResponseEntity.badRequest().body(responseDto);
            }
            IUser user = userService.findByEmail(findIdDto.getEmail());
            if( user != null && user.getName().equals( findIdDto.getName() ) ){
                responseDto.setLoginId(user.getLoginId());
                responseDto.setMessage("ID 찾기 성공");
                return ResponseEntity.ok().body(responseDto);
            }else{
                responseDto.setMessage("일치하는 ID가 없습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
            }
        }catch (Exception ex){
            log.error(ex.toString());
            responseDto.setMessage("찾기 실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }

    @PostMapping("/resetpw")
    private ResponseEntity<ResetPwResponseDto> resetPw(@Validated @RequestBody ResetPwDto resetPwDto){
        ResetPwResponseDto responseDto = new ResetPwResponseDto();
        try{
            if( resetPwDto.getLoginId().isEmpty() || resetPwDto.getName().isEmpty() || resetPwDto.getEmail().isEmpty() ){
                responseDto.setMessage("loginId와 name, email을 입력하십시오.");
                return ResponseEntity.badRequest().body(responseDto);
            }
            IUser user = userService.findByEmail(resetPwDto.getEmail());
            if( user != null && user.getLoginId().equals(resetPwDto.getLoginId()) && user.getName().equals(resetPwDto.getName()) ){
                String randomValue = getRandomString(8);    //  숫자 + 문자 + 특수문자 조합 문자열 생성
                user.setPassword(randomValue);
                userService.changePassword(user);
                responseDto.setPassword(user.getPassword());
                responseDto.setMessage("Pw 초기화 성공");
                return ResponseEntity.ok().body(responseDto);
            } else{
                responseDto.setMessage("등록된 계정이 존재하지 않습니다.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
            }
        }catch (Exception ex){
            log.error(ex.toString());
            responseDto.setMessage("Pw 초기화 실패");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseDto);
        }
    }

    private final char[] randomCharSet = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '!', '@', '#', '^', '?', '*', '+'
            // 원하는 특수문자 추가해서 사용
    };

    public String getRandomString(int size){
        StringBuilder sb = new StringBuilder();
        Random random = new Random(new Date().getTime());

        int len = randomCharSet.length;

        for( int i=0; i<size; i++ ){
            // random.nextInt(len)은 0부터 randomCharSet.length - 1 사이의 값을 무작위로 반환
            sb.append(randomCharSet[random.nextInt(len)]);
        }
        return sb.toString();
    }

    @GetMapping("/info/{id}")
    private ResponseEntity<ResponseDto> userInfo(@PathVariable Long id){
        try{
            IUser user = this.userService.findById(id);
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"OK",user);

        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999,ex.getMessage(), null);
        }
    }

    @PostMapping("/changepw")
    private ResponseEntity<ResponseDto> changePw(@Validated @RequestBody ChangePwDto changePwDto, Model model){
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            IUser user = cudInfoDto.getLoginUser();
            if(changePwDto.getNewPassword().length() <5){
                return makeResponseEntity(HttpStatus.BAD_REQUEST,ResponseCode.R888881,"비밀번호는 5글자 이상으로 정하셔야 합니다.",null);
            }
            if(!changePwDto.getNewPassword().equals(changePwDto.getCheckNewPassword())){
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R888881, "비밀번호가 일치하지 않습니다.", null);
            }
            user.setPassword(changePwDto.getNewPassword());
            userService.changePassword(user);
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"OK",user);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999,"비밀번호 변경 실패",null);
        }
    }

    @Override
    public ResponseEntity<ResponseDto> insert(Model model, Object dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> update(Model model, Long id, Object dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> deleteById(Model model, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> findById(Model model, Long id) {
        return null;
    }
}
