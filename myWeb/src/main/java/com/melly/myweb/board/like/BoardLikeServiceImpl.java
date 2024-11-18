package com.melly.myweb.board.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardLikeServiceImpl implements IBoardLikeService{
    @Autowired
    private IBoardLikeMybatisMapper boardLikeMybatisMapper;


    @Override
    public Integer countByLike(IBoardLike boardLike) {
        if( boardLike == null || boardLike.getBoardId() == null
                || boardLike.getCreateId() == null || boardLike.getBoardId() <= 0 ){

            return 0;
        }

        BoardLikeDto boardLikeDto = BoardLikeDto.builder().build();
        boardLikeDto.copyFields(boardLike);
        Integer count = this.boardLikeMybatisMapper.countByLike(boardLikeDto);

        return count;
    }
}
