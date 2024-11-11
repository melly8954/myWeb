package com.melly.myweb.security.controller;

import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.inif.IResponseController;
import com.melly.myweb.security.dto.SignUpRequestDto;
import com.melly.myweb.user.IUser;
import com.melly.myweb.user.IUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/selogin")
public class LoginSessionController implements IResponseController {
    @Autowired
    private IUserService userService;

    @GetMapping("/signup")
    private String viewSignUp(Model model){
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            return "redirect:/";
        } catch (Exception ex) {
            return "login/signup";
        }
    }

    @PostMapping("/signup")
    private String signUp(Model model, @Valid @ModelAttribute SignUpRequestDto signUpRequestDto, BindingResult bindingResult){
        try{
            if( signUpRequestDto == null ){
                return "redirect:/";
            }
            if(bindingResult.hasErrors()){
                List<String> errorList = new ArrayList<>();
                for( FieldError error : bindingResult.getFieldErrors()){
                    errorList.add(error.getField() + " : " + error.getDefaultMessage());
                    log.info(error.getDefaultMessage());
                }
                model.addAttribute("errorList",errorList);
                return "login/fail";
            }
            CUDInfoDto cudInfoDto = new CUDInfoDto(signUpRequestDto);
            IUser IUser = this.userService.insert(cudInfoDto,signUpRequestDto);
        } catch (Exception ex){
            log.error(ex.toString());
            model.addAttribute("message","회원 가입을 실패 했습니다. 입력 정보를 다시 확인하거나 관리자에게 문의하세요");
            return "login/fail";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    private String logout(HttpServletResponse response){
        // /logout 은 스프링 security 에서 처리하므로 이쪽 url 로 오지 않음
        return "login/signout";
    }

//    @GetMapping("/signout")
//    private String signout(HttpSession session, HttpServletResponse response) {
//        Cookie cookie = new Cookie("loginId", null);
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
//        session.invalidate();
//        return "login/signout";
//    }

}
