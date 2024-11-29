package com.melly.myweb.board.commentlike;

public interface ICommentLike {
    Long getId();
    void setId(Long id);

    Long getCommentId();
    void setCommentId(Long commentId);

    Long getCreateId();
    void setCreateId(Long createId);

    String getCommentTbl();
    void setCommentTbl(String commentTbl);

    default void copyFields(ICommentLike from){
        if(from == null){
            return;
        }
        if(from.getCommentId() != null){
            this.setCommentId(from.getCommentId());
        }
        if(from.getCreateId() != null){
            this.setCreateId(from.getCreateId());
        }
        if(from.getCommentTbl() != null){
            this.setCommentTbl(from.getCommentTbl());
        }
    }
}
