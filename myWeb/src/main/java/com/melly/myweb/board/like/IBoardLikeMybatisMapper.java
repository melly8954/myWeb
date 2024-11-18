package com.melly.myweb.board.like;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IBoardLikeMybatisMapper {
    void insert(BoardLikeDto boardLikeDto);
    void update(BoardLikeDto boardLikeDto);
    void delete(BoardLikeDto boardLikeDto);
    void deleteById(Long id);
    BoardLikeDto findById(Long id);

    Integer countByLike(BoardLikeDto boardLikeDto);
}
