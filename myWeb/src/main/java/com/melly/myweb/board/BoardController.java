package com.melly.myweb.board;


import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.exception.LoginAccessException;
import com.melly.myweb.commons.inif.IResponseController;
import com.melly.myweb.security.config.SecurityConfig;
import com.melly.myweb.user.IUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController implements IResponseController {
    @Autowired
    private IBoardService boardService;

    @GetMapping("/board_list")
    public String boardList(Model model) {
        try{
            makeResponseCheckLogin(model);
            List<BoardDto> list =  this.boardService.findAllByNameContains();
            model.addAttribute("boardList",list);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return "redirect:/selogin/login";
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return "board/boardList";
    }

    @GetMapping("/board_insert_page")
    public String boardInsert(Model model){
        try{
            makeResponseCheckLogin(model);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return "redirect:/selogin/login";
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return "board/boardInsert";
    }

    @PostMapping("/board_insert")
    public String boardInsert(Model model,@ModelAttribute BoardDto boardDto){
        // @ModelAttribute CategoryDto dto : POST 방식의 요청은 값이 숨겨져 있다. HTTP Request web form
        //  :  주소에서 ?searchName=&page=값 변수의 값을 받는다. "application/x-www-form-?????"
        try {
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);

            this.boardService.insert(cudInfoDto,boardDto);

        } catch (Exception ex) {
            log.error(ex.toString()); // error 응답
        }
        return "redirect:/board/board_list";
    }

    @GetMapping("/board_view")
    public String boardView(Model model, @RequestParam Long id){
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            IBoard find  = this.boardService.findById(id);
            model.addAttribute("boardView",find);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return "board/boardView";
    }
}
