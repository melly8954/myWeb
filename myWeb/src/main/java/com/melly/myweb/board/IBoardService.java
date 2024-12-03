package com.melly.myweb.board;

import com.melly.myweb.board.file.BoardFileDto;
import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.inif.IServiceCRUD;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface IBoardService extends IServiceCRUD<BoardDto> {
//    List<BoardDto> findGetAll();
    Integer countAllByNameContains(SearchQueryDto searchQueryDto);
    List<BoardDto> findAllByNameContains(SearchQueryDto searchQueryDto);

    void addViewQty(Long id,Long userId);
    void addLikeQty(CUDInfoDto cudInfoDto, Long id);
    void subLikeQty(CUDInfoDto cudInfoDto, Long id);

    BoardDto insert(CUDInfoDto info, BoardDto dto, List<MultipartFile> files) throws RuntimeException;
    BoardDto update(CUDInfoDto info, BoardDto dto, List<BoardFileDto> boardFileDtoList, List<MultipartFile> files) throws RuntimeException;

}
