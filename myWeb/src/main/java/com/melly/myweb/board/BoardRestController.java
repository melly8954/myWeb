package com.melly.myweb.board;

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
public class BoardRestController implements ICommonRestController {
    @Autowired
    private IBoardService boardService;

    @GetMapping("/showBoardList")
    public ResponseEntity<ResponseDto> findAllBoardList(Model model){
        try{
            makeResponseCheckLogin(model);
            List<BoardDto> list = this.boardService.findAllByNameContains();
            return makeResponseEntity(HttpStatus.OK,ResponseCode.R000000,"성공",list);
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
    public ResponseEntity<ResponseDto> insert(Model model, Object dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseDto> update(Model model, Long id, Object dto) {
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
