package com.melly.myweb.board.comment;

import com.melly.myweb.board.commentlike.CommentLikeDto;
import com.melly.myweb.board.commentlike.ICommentLikeService;
import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.ResponseCode;
import com.melly.myweb.commons.dto.ResponseDto;
import com.melly.myweb.commons.exception.IdNotFoundException;
import com.melly.myweb.commons.exception.LoginAccessException;
import com.melly.myweb.commons.inif.ICommonRestController;
import com.melly.myweb.user.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board_comment")
public class BoardCommentRestController implements ICommonRestController<BoardCommentDto> {

    @Autowired
    private IBoardCommentService boardCommentService;

    @Autowired
    private ICommentLikeService commentLikeService;

    @Override
    @PostMapping("/insert")
    public ResponseEntity<ResponseDto> insert(Model model, @Validated @RequestBody BoardCommentDto boardCommentDto) {
        try {
            if(boardCommentDto == null){
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            BoardCommentDto insert = this.boardCommentService.insert(cudInfoDto,boardCommentDto);
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",insert);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R888881,ex.getMessage(),null);
        }catch (IdNotFoundException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND,ResponseCode.R000041,ex.getMessage(),null);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999,ex.getMessage(),null);
        }
    }

    @Override
    @PatchMapping("/update/{id}")
    public ResponseEntity<ResponseDto> update(Model model, @Validated @PathVariable Long id, BoardCommentDto boardCommentDto) {
        try{
            if(boardCommentService== null){
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            IBoardComment find = this.boardCommentService.findById(id);
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            BoardCommentDto update = this.boardCommentService.update(cudInfoDto,boardCommentDto);
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",update);
        }catch  (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R888881,ex.getMessage(),null);
        }catch (IdNotFoundException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND,ResponseCode.R000041,ex.getMessage(),null);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999,ex.getMessage(),null);
        }
    }

    @DeleteMapping("/deleteFlag/{id}")
    public ResponseEntity<ResponseDto> updateDeleteFlag(Model model, @Validated @PathVariable Long id, @Validated @RequestBody BoardCommentDto boardCommentDto) {
        try {
            if (id == null || boardCommentDto == null || boardCommentDto.getId() == null
                    || boardCommentDto.getId() <= 0 || !id.equals(boardCommentDto.getId()) || boardCommentDto.getDeleteFlag() == null) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoardComment find = this.boardCommentService.findById(id);
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            Boolean result = this.boardCommentService.updateDeleteFlag(cudInfoDto, boardCommentDto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteById(Model model, @Validated @PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            IBoardComment find = this.boardCommentService.findById(id);
            makeResponseCheckLogin(model);
            Boolean result = this.boardCommentService.deleteById(id);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> findById(Model model, @Validated @PathVariable Long id) {
        return null;
    }

    @PostMapping("/findbyboardid")
    public ResponseEntity<ResponseDto> findByBoardId(Model model
            , @Validated @RequestBody SearchBoardCommentDto searchBoardCommentDto) {
        try {
            if ( searchBoardCommentDto == null ) {
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051, "입력 매개변수 에러", null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            List<BoardCommentDto> result = this.boardCommentService.findAllByBoardId(cudInfoDto.getLoginUser(), searchBoardCommentDto);
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "OK", result);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN, ResponseCode.R888881, ex.getMessage(), null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND, ResponseCode.R000041, ex.getMessage(), null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ResponseCode.R999999, ex.getMessage(), null);
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> addLikeQty(Model model, @Validated @PathVariable Long id){
        try{
            if(id == null || id <= 0){
                return makeResponseEntity(HttpStatus.BAD_REQUEST,ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            IBoardComment find = this.boardCommentService.findById(id);
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);

            this.boardCommentService.addLikeQty(cudInfoDto,id);
            IBoardComment result = this.getCommentAndLike(id,cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",result);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R000081, ex.getMessage(),null);
        }catch (IdNotFoundException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R000081, ex.getMessage(),null);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999, ex.getMessage(),null);
        }
    }

    @GetMapping("/unlike/{id}")
    public ResponseEntity<ResponseDto> subLikeQty(Model model, @Validated @PathVariable Long id){
        try{
            if(id == null || id <= 0){
                return makeResponseEntity(HttpStatus.BAD_REQUEST,ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            IBoardComment find = this.boardCommentService.findById(id);
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);

            this.boardCommentService.subLikeQty(cudInfoDto,id);
            IBoardComment result = this.getCommentAndLike(id,cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",result);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R000081, ex.getMessage(),null);
        }catch (IdNotFoundException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R000081, ex.getMessage(),null);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999, ex.getMessage(),null);
        }
    }

    private IBoardComment getCommentAndLike(Long id, IUser loginUser){
        IBoardComment result = this.boardCommentService.findById(id);
        CommentLikeDto commentLikeDto = CommentLikeDto.builder()
                .commentId(id)
                .createId(loginUser.getId())
                .commentTbl(new BoardCommentDto().getTbl()).build();
        Integer likeCount = this.commentLikeService.countCommentLike(commentLikeDto);
        result.setCommentLikeRecoder(likeCount == 1);    // 1일 경우 true -> 좋아요를 누른 경우
        return result;
    }
}
