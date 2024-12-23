package com.melly.myweb.board;


import com.melly.myweb.board.comment.BoardCommentDto;
import com.melly.myweb.board.comment.IBoardCommentService;
import com.melly.myweb.board.file.BoardFileDto;
import com.melly.myweb.board.file.IBoardFile;
import com.melly.myweb.board.file.IBoardFileService;
import com.melly.myweb.board.like.BoardLikeDto;
import com.melly.myweb.board.like.IBoardLikeService;
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
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController implements IResponseController {
    @Autowired
    private IBoardService boardService;
    @Autowired
    private IBoardLikeService boardLikeService;
    @Autowired
    private IBoardFileService boardFileService;

    @GetMapping("/board_list")
    public String boardList(Model model,@ModelAttribute SearchQueryDto searchQueryDto) {
        try{
            makeResponseCheckLogin(model);
            List<BoardDto> list =  this.boardService.findAllByNameContains(searchQueryDto);
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
            this.boardService.addViewQty(id, find.getId());

            // 게시판에 좋아요를 누른 사용자 확인
            BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                    .tbl(new BoardDto().getTbl())
                    .createId(cudInfoDto.getLoginUser().getId())
                    .boardId(id)
                    .build();
            Integer likeCount = this.boardLikeService.countByLike(boardLikeDto);
            find.setLikeRecord(likeCount == 1);     // 1일 경우 true -> 좋아요를 누른 경우
            find.copyFields(find);

            model.addAttribute("boardView",find);

            // 파일 조회
            IBoardFile boardFile = BoardFileDto.builder()
                    .tbl(new BoardDto().getTbl())
                    .boardId(id)
                    .build();
            List<IBoardFile> files = this.boardFileService.findAllByTblBoardId(boardFile);

            model.addAttribute("files", files);  // 파일 리스트 추가

        }catch (LoginAccessException ex){
            log.error(ex.toString());
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return "board/boardView";
    }

    @GetMapping("/board_update_page")
    public String boardUpdatePage(Model model, @RequestParam Long id){
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            IBoard find  = this.boardService.findById(id);
            model.addAttribute("board_list",find);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return "board/boardUpdate";
    }

    @PostMapping("/board_update")
    public String boardUpdate(Model model, @RequestParam Long id, @ModelAttribute BoardDto boardDto){
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardService.update(cudInfoDto,boardDto);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return "redirect:/board/board_list";
    }

    @GetMapping("/board_delete")
    public String boardDelete(Model model, @RequestParam Long id){
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            BoardDto find = this.boardService.findById(id);
            this.boardService.updateDeleteFlag(cudInfoDto,find);
            this.boardService.deleteById(id);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return "redirect:/board/board_list";
    }
}
