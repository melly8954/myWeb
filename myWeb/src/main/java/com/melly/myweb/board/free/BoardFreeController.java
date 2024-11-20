package com.melly.myweb.board.free;

import com.melly.myweb.board.like.IBoardLikeService;
import com.melly.myweb.commons.exception.LoginAccessException;
import com.melly.myweb.commons.inif.IResponseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardFreeController implements IResponseController {
    @Autowired
    private IBoardFreeService boardFreeService;
    @Autowired
    private IBoardLikeService boardLikeService;


    // 게시판 목록 화면
    @GetMapping("/board_list")
    public String boardList(@ModelAttribute("searchBoardDto") SearchBoardDto searchBoardDto, Model model){
        try{
            makeResponseCheckLogin(model);

            List<BoardFreeDto> list = this.boardFreeService.findAllByNameContains(searchBoardDto);
            model.addAttribute("boardList",list);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return "redirect:/selogin/login";
        }catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardList";
    }

    // 게시판 글 추가 화면
    @GetMapping("/board_insert")
    public String boardInsert(Model model){
        return "board/boardInsert";
    }
}
