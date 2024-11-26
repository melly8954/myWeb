package com.melly.myweb.board;

import com.melly.myweb.board.like.BoardLikeDto;
import com.melly.myweb.board.like.IBoardLikeMybatisMapper;
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

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board")
public class BoardRestController implements ICommonRestController<BoardDto> {
    @Autowired
    private IBoardService boardService;

    @Autowired
    private IBoardLikeMybatisMapper boardLikeMybatisMapper;

    @Override
    @PostMapping("boardInsert")
    public ResponseEntity<ResponseDto> insert(Model model, @Validated @RequestBody BoardDto boardDto) {
        try{
            CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
            BoardDto insert = this.boardService.insert(cudInfoDto,boardDto);
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",insert);
        }catch (LoginAccessException ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.FORBIDDEN,ResponseCode.R888881, ex.getMessage(),null);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999, ex.getMessage(),null);
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

        Integer likeCount = this.boardLikeMybatisMapper.countByLike(boardLikeDto);
        result.setDeleteDt(likeCount.toString());
        return result;
    }
    @Override
    public ResponseEntity<ResponseDto> update(Model model, Long id, BoardDto dto) {
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
