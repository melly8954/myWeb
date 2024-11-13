package com.melly.myweb.security.controller;

import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.inif.IResponseController;
import com.melly.myweb.security.config.SecurityConfig;
import com.melly.myweb.security.dto.LoginRequestDto;
import com.melly.myweb.security.dto.SignUpRequestDto;
import com.melly.myweb.user.IUser;
import com.melly.myweb.user.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/login")
    private String viewLogin(Model model){
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            return "redirect:/";
        } catch(Exception ex){
            return "login/login";
        }
    }

    @PostMapping("signin")
    private String signin(Model model, @ModelAttribute LoginRequestDto loginRequestDto, HttpServletRequest request){
        try{
            if(loginRequestDto == null){
                return "redirect:/";
            }
            try{
                CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
                return "redirect:/";
            }catch (Exception ex){
            }
            IUser loginUser = this.userService.login(loginRequestDto);
            if(loginUser == null){
                model.addAttribute("message","로그인 실패(id와 pw를 확인하세요)");
                return "login/login";
            }
            HttpSession session = request.getSession();
            session.setAttribute(SecurityConfig.LOGINUSER,loginUser.getNickname());
            session.setMaxInactiveInterval(60*60); // 세션별 유효시간 지정
        }catch (Exception ex){
            log.error(ex.toString());
            model.addAttribute("message","로그인 실패, 관리자에게 문의하십시오.");
            return "login/login";
        }
        return "redirect:/";
    }

    @PostMapping("/idCheck")
    @ResponseBody
    public int idCheck(@RequestParam String loginId){
        return userService.idCheck(loginId);
    }

    @PostMapping("/nicknameCheck")
    @ResponseBody
    public int nicknameCheck(@RequestParam String nickname){
        return userService.nicknameCheck(nickname);
    }

    @PostMapping("/emailCheck")
    @ResponseBody
    public int emailCheck(@RequestParam String email){
        return userService.emailCheck(email);
    }

    @GetMapping("/findId")
    private String viewFindId(){
        return "login/findId";
    }

    @PostMapping("/findId")
    private String findId(@ModelAttribute SignUpRequestDto signUpRequestDto, Model model){
        try{
            if(signUpRequestDto == null){
                return "redirect:/";
            }
            IUser findId = this.userService.findByEmail(signUpRequestDto.getEmail());
            model.addAttribute("findId",findId.getLoginId());
            return "login/idResult";
        }catch (Exception ex){
            log.error(ex.getMessage());
            return "login/findId";
        }
    }

    @GetMapping("/resetPw")
    private String resetPw(){
        return "login/resetPw";
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
