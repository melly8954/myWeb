package com.melly.myweb.board;

import com.melly.myweb.board.file.BoardFileDto;
import com.melly.myweb.board.like.BoardLikeDto;
import com.melly.myweb.board.like.IBoardLikeMybatisMapper;
import com.melly.myweb.board.like.IBoardLikeService;
import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.ResponseCode;
import com.melly.myweb.commons.dto.ResponseDto;
import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.exception.IdNotFoundException;
import com.melly.myweb.commons.exception.LoginAccessException;
import com.melly.myweb.commons.inif.ICommonRestController;
import com.melly.myweb.commons.inif.IResponseController;
import com.melly.myweb.user.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
public class BoardRestController implements ICommonRestController<BoardDto> {
    @Autowired
    private IBoardService boardService;

    @Autowired
    private IBoardLikeService boardLikeService;

    @PostMapping("insert")
    public ResponseEntity<ResponseDto> insert(Model model, @Validated @RequestPart(value="boardDto") BoardDto boardDto,
                                                            @RequestPart(value="files", required = false) List<MultipartFile> files) {
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            BoardDto insert = this.boardService.insert(cudInfoDto,boardDto,files);
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",insert);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R888881, ex.getMessage(),null);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999, ex.getMessage(),null);
        }
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<ResponseDto> update(Model model, @Validated @PathVariable("id") Long id
                                            ,@Validated @RequestBody BoardDto boardDto){
        try{
            if( id == null || boardDto == null || boardDto.getId() == null || boardDto.getId() <= 0
            || !id.equals(boardDto.getId()) ){
                return makeResponseEntity(HttpStatus.BAD_REQUEST,ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            BoardDto result = this.boardService.update(cudInfoDto,boardDto);
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",result);
        }catch (LoginAccessException ex) {
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

//    @GetMapping("/showBoardList")
//    public ResponseEntity<ResponseDto> findAllBoardList(Model model){
//        try{
//            makeResponseCheckLogin(model);
//            List<BoardDto> list = this.boardService.findGetAll();
//            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",list);
//        }catch (LoginAccessException ex){
//            log.error(ex.toString());
//            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R888881, ex.getMessage(),null);
//        }catch (IdNotFoundException ex){
//            log.error(ex.toString());
//            return makeResponseEntity(HttpStatus.NOT_FOUND,ResponseCode.R000041, ex.getMessage(),null);
//        }catch (Exception ex){
//            log.error(ex.toString());
//            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999, ex.getMessage(),null);
//        }
//    }

    @PostMapping("/searchTitle")
    public ResponseEntity<ResponseDto> findAllByNameContains(Model model, @Validated @RequestBody SearchQueryDto searchQueryDto){
        try{
            if(searchQueryDto == null){
                return makeResponseEntity(HttpStatus.BAD_REQUEST,ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            makeResponseCheckLogin(model);
            int total = this.boardService.countAllByNameContains(searchQueryDto);
            List<BoardDto> list = this.boardService.findAllByNameContains(searchQueryDto);
            searchQueryDto.setTotal(total);
            searchQueryDto.setDataList(list);
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",searchQueryDto);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R888881, ex.getMessage(),null);
        }catch (IdNotFoundException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND,ResponseCode.R000041, ex.getMessage(),null);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999, ex.getMessage(),null);
        }
    }

    @PostMapping("/countTitle")
    public ResponseEntity<ResponseDto> countAllByNameContains(Model model, @Validated @RequestBody SearchQueryDto searchQueryDto){
        try{
            if(searchQueryDto == null){
                return makeResponseEntity(HttpStatus.BAD_REQUEST,ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            makeResponseCheckLogin(model);
            Integer result = this.boardService.countAllByNameContains(searchQueryDto);
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",result);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R888881, ex.getMessage(),null);
        }catch (IdNotFoundException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND,ResponseCode.R000041, ex.getMessage(),null);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999, ex.getMessage(),null);
        }
    }

    @GetMapping("/like/{id}")
    public ResponseEntity<ResponseDto> addLikeQty(Model model, @Validated @PathVariable Long id){
        try{
            if(id == null || id <= 0){
                return makeResponseEntity(HttpStatus.BAD_REQUEST,ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardService.addLikeQty(cudInfoDto,id);
            IBoard result = this.getBoardAndLike(id,cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",result);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R888881, ex.getMessage(),null);
        }catch (IdNotFoundException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND,ResponseCode.R000041, ex.getMessage(),null);
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
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            this.boardService.subLikeQty(cudInfoDto,id);
            IBoard result = this.getBoardAndLike(id,cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",result);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R888881, ex.getMessage(),null);
        }catch (IdNotFoundException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.NOT_FOUND,ResponseCode.R000041, ex.getMessage(),null);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999, ex.getMessage(),null);
        }
    }


    private IBoard getBoardAndLike(Long id, IUser loginUser){
        IBoard result = this.boardService.findById(id);
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .tbl(new BoardDto().getTbl())
                .createId(loginUser.getId())
                .boardId(id)
                .build();

        Integer likeCount = this.boardLikeService.countByLike(boardLikeDto);
        result.setLikeRecord(likeCount == 1);       // 1일 경우 true -> 좋아요를 누른 경우
        return result;
    }

    
    
    
    // 사용하지 않을 오버라이딩 메서드들
    @Override
    public ResponseEntity<ResponseDto> insert(Model model, BoardDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> updateDeleteFlag(Model model, Long id, BoardDto dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> deleteById(Model model, Long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> findById(Model model, Long id) {
        return null;
    }
}
