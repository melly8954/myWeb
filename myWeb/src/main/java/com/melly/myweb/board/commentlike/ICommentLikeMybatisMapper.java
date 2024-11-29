package com.melly.myweb.board.commentlike;

import com.melly.myweb.commons.inif.IMybatisCRUD;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ICommentLikeMybatisMapper extends IMybatisCRUD<CommentLikeDto> {
    void deleteByCommentLike(CommentLikeDto commentLikeDto);
    Integer countByCommentLike(CommentLikeDto commentLikeDto);

}
