package com.melly.myweb.board.free;

import com.melly.myweb.board.like.IBoardLikeService;
import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.ResponseCode;
import com.melly.myweb.commons.dto.ResponseDto;
import com.melly.myweb.commons.inif.ICommonRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/board")
public class BoardFreeRestController implements ICommonRestController {
    @Autowired
    private IBoardFreeService boardFreeService;
    @Autowired
    private IBoardLikeService boardLikeService;

    @PostMapping("/id")
    public ResponseEntity<ResponseDto> boardInsert(@Validated  @RequestBody BoardFreeDto boardFreeDto, Model model){
        CUDInfoDto cudInfoDto = makeResponseCheckLogin(model);
        try{
            if( boardFreeDto.getId() == null || boardFreeDto.getId() <= 0 ){
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            BoardFreeDto insert = (BoardFreeDto) this.boardFreeService.insert(boardFreeDto,cudInfoDto.getLoginUser());
            return makeResponseEntity(HttpStatus.OK, ResponseCode.R000000, "성공",insert);
        }catch (Exception ex){
            log.error(ex.toString());
            return makeResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR,ResponseCode.R999999,ex.toString(),null);
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
