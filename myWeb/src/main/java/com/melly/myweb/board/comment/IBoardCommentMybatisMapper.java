package com.melly.myweb.board.comment;

import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardCommentMybatisMapper extends IMybatisCRUD<BoardCommentDto> {
    List<BoardCommentDto> findAllByBoardId(SearchBoardCommentDto searchBoardCommentDto);
    Integer countAllByBoardId(SearchQueryDto searchQueryDto);

    void addLikeQty(Long id);
    void subLikeQty(Long id);

}
