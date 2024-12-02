package com.melly.myweb.board.file;

import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardFileMybatisMapper extends IMybatisCRUD<BoardFileDto> {
    List<BoardFileDto> findAllByTblBoardId(BoardFileDto boardFileDto);
    Integer countAllByTblBoardId(BoardFileDto boardFileDto);
}
