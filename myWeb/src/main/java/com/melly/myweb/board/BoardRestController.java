package com.melly.myweb.board;

import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.ResponseCode;
import com.melly.myweb.commons.dto.ResponseDto;
import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.exception.IdNotFoundException;
import com.melly.myweb.commons.exception.LoginAccessException;
import com.melly.myweb.commons.inif.ICommonRestController;
import com.melly.myweb.commons.inif.IResponseController;
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
