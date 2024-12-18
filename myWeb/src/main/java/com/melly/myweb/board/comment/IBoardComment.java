package com.melly.myweb.board.comment;

import com.melly.myweb.commons.dto.IBase;

public interface IBoardComment extends IBase{
    Long getId();
    void setId(Long id);

    String getComment();
    void setComment(String comment);

    Integer getLikeQty();
    void setLikeQty(Integer likeQty);

    Long getBoardId();
    void setBoardId(Long boardId);

    String getTbl();

    Boolean getCommentLikeRecoder();
    void setCommentLikeRecoder(Boolean commentLikeRecoder);

    default void copyFields(IBoardComment from){
        if(from == null){
            return;
        }
        if(from.getId() != null){
            this.setId((from.getId()));
        }
        if(from.getComment() != null && !from.getComment().isEmpty()){
            this.setComment(from.getComment());
        }
        if(from.getLikeQty() != null){
            this.setLikeQty(from.getLikeQty());
        }
        if(from.getBoardId() != null){
            this.setBoardId(from.getBoardId());
        }
        if(from.getCommentLikeRecoder() != null){
            this.setCommentLikeRecoder(from.getCommentLikeRecoder());
        }
        IBase.super.copyFields(from);
    }
}
