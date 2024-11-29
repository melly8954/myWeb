package com.melly.myweb.board.commentlike;

import com.melly.myweb.commons.inif.IServiceCRUD;

public interface ICommentLikeService extends IServiceCRUD<CommentLikeDto> {
    Integer countCommentLike(CommentLikeDto commentLikeDto);

}
