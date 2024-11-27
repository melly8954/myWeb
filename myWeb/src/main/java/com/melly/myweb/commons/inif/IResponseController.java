package com.melly.myweb.commons.inif;

import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.exception.LoginAccessException;
import com.melly.myweb.security.config.SecurityConfig;
import com.melly.myweb.user.IUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.melly.myweb.commons.dto.ResponseCode;
import com.melly.myweb.commons.dto.ResponseDto;

public interface IResponseController {
    default CUDInfoDto makeResponseCheckLogin(Model model){
        IUser loginUser = (IUser) model.getAttribute(SecurityConfig.LOGINUSER);
        if( loginUser == null ){
            throw new LoginAccessException("로그인 필요");
        }
        return new CUDInfoDto(loginUser);
    }

    default ResponseEntity<ResponseDto> makeResponseEntity(HttpStatus httpStatus
            , ResponseCode responseCode
            , String message
            , Object responseData) {
        ResponseDto dto = ResponseDto.builder()
                .responseCode(responseCode)
                .message(message)
                .responseData(responseData)
                .build();
        return ResponseEntity.status(httpStatus).body(dto);
    }



}
