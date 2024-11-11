package com.melly.myweb.security.controller;

import com.melly.myweb.security.config.SecurityConfig;
import com.melly.myweb.user.IUser;
import com.melly.myweb.user.IUserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequestMapping("/")
public class indexController {
    @Autowired
    private IUserService userService;

    @GetMapping("")
    private String indexPage(Model model, @SessionAttribute(name= SecurityConfig.LOGINUSER, required = false) String nickname){
        try{
            if( nickname != null ){
                IUser loginUser = this.userService.findByNickname(nickname);
                model.addAttribute(SecurityConfig.LOGINUSER,loginUser);
            }
        }catch(Exception ex){
            log.error(ex.toString());
        }
        return "index";
    }

    @GetMapping("/signout")
    private String signout(HttpServletResponse response, HttpSession session){
        session.invalidate();

        Cookie cookie = new Cookie(SecurityConfig.LOGINUSER,null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "login/signout";
    }
}
