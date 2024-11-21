package com.melly.myweb.board;


import com.melly.myweb.security.config.SecurityConfig;
import com.melly.myweb.user.IUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
    @GetMapping("/board_ajx_list")
    private String boardAjxList(
            Model model,
            HttpServletRequest request,
            HttpServletResponse response,
//            HttpCookie cookie,
//            HttpHeaders headers,
            HttpSession session
    ) {
        IUser loginUser = (IUser)model.getAttribute(SecurityConfig.LOGINUSER);
        if ( loginUser == null ) {
            return "redirect:/";
        }
        model.addAttribute("boardTbl", new BoardDto().getTbl());
        return "board/boardList";
    }
}
