package com.melly.myweb.security.controller;

import com.melly.myweb.commons.dto.ResponseCode;
import com.melly.myweb.commons.dto.ResponseDto;
import com.melly.myweb.commons.inif.ICommonRestController;
import com.melly.myweb.security.config.SecurityConfig;
import com.melly.myweb.security.dto.FindIdDto;
import com.melly.myweb.security.dto.FindIdResponseDto;
import com.melly.myweb.security.dto.LoginRequestDto;
import com.melly.myweb.user.IUser;
import com.melly.myweb.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("findId")
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
