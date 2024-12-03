package com.melly.myweb.board.file;

import com.melly.myweb.commons.dto.ResponseCode;
import com.melly.myweb.commons.dto.ResponseDto;
import com.melly.myweb.commons.exception.IdNotFoundException;
import com.melly.myweb.commons.exception.LoginAccessException;
import com.melly.myweb.commons.inif.IResponseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/board_file")
public class BoardFileRestController implements IResponseController {
    @Autowired
    private IBoardFileService boardFileService;

    @PostMapping("/findbyboardid")
    public ResponseEntity<ResponseDto> findByBoardId(Model model, @Validated @RequestBody BoardFileDto boardFileDto){
        try{
            if(boardFileDto == null){
                return makeResponseEntity(HttpStatus.BAD_REQUEST, ResponseCode.R000051,"입력 매개변수 에러",null);
            }
            makeResponseCheckLogin(model);
            List<IBoardFile> result = this.boardFileService.findAllByTblBoardId(boardFileDto);
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

    @GetMapping(path = "/viewimage/{id}")
    public ResponseEntity<byte[]> viewimage(Model model
            , @PathVariable Long id) {
        try{
            if ( id == null || id <= 0 ) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentLength(0).body(null);
            }
            makeResponseCheckLogin(model);
            IBoardFile find = this.boardFileService.findById(id);
            byte[] bytes = this.boardFileService.getBytesFromFile(find);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/" + this.getImageType(find));
            headers.add("Content-Length", find.getLength().toString());
            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentLength(0).body(null);
        } catch (IdNotFoundException ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).contentLength(0).body(null);
        } catch (Exception ex) {
            log.error(ex.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentLength(0).body(null);
        }
    }

    private String getImageType(IBoardFile dto) {
        if (".jfif".equalsIgnoreCase(dto.getFileType())
                || ".jpg".equalsIgnoreCase(dto.getFileType())
                || ".jpeg".equalsIgnoreCase(dto.getFileType())
                || ".jfif".equalsIgnoreCase(dto.getFileType())) {
            return "jpeg";
        } else if (".png".equalsIgnoreCase(dto.getFileType())) {
            return "png";
        } else if (".gif".equalsIgnoreCase(dto.getFileType())) {
            return "gif";
        }
        return "";
    }
}
