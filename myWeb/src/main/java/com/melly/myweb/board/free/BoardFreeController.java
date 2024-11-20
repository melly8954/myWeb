package com.melly.myweb.board.free;

import com.melly.myweb.board.like.IBoardLikeService;
import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.exception.LoginAccessException;
import com.melly.myweb.commons.inif.IResponseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

            int total = this.boardFreeService.countAllByNameContains(searchBoardDto);
            List<BoardFreeDto> list = this.boardFreeService.findAllByNameContains(searchBoardDto);
            searchBoardDto.setTotal(total);

            model.addAttribute("boardList",list);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return "redirect:/selogin/login";
        }catch (Exception e) {
            log.error(e.toString());
        }
        return "board/boardList";
    }

    // 게시판 글 추가 화면
    @GetMapping("/board_insert")
    public String boardInsert(Model model){
        try {
            makeResponseCheckLogin(model);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/selogin/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "board/boardInsert";
    }


}
