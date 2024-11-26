package com.melly.myweb.board;

import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardMybatisMapper extends IMybatisCRUD<BoardDto> {
    void addViewQty(Long id);
    void addLikeQty(Long id);
    void subLikeQty(Long id);

    Integer countAllByNameContains(SearchQueryDto searchQueryDto);
    List<BoardDto> findGetAll();
    List<BoardDto> findAllByNameContains(SearchQueryDto searchQueryDto);
}
