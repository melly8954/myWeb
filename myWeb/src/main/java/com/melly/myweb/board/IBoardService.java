package com.melly.myweb.board;

import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.inif.IServiceCRUD;


import java.io.IOException;
import java.util.List;

public interface IBoardService extends IServiceCRUD<BoardDto> {
    List<BoardDto> findAllByNameContains();
    Integer countAllByNameContains(SearchQueryDto searchQueryDto);

    void addViewQty(Long id);
    void addLikeQty(CUDInfoDto cudInfoDto, Long id);
    void subLikeQty(CUDInfoDto cudInfoDto, Long id);
}
