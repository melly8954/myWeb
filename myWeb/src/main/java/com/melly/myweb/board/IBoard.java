package com.melly.myweb.board;

import com.melly.myweb.commons.dto.IBase;

public interface IBoard extends IBase {
    Long getId();
    void setId(Long id);

    String getTitle();
    void setTitle(String title);

    String getContent();
    void setContent(String content);

    Integer getViewQty();
    void setViewQty(Integer viewQty);

    Integer getLikeQty();
    void setLikeQty(Integer likeQty);

    String getTbl();

    Boolean getLikeRecord();
    void setLikeRecord(Boolean likeRecord);

    String getTotalComment();
    void setTotalComment(String totalComment);

    default void copyFields(IBoard from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getTitle() != null && !from.getTitle().isEmpty()) {
            this.setTitle(from.getTitle());
        }
        if (from.getContent() != null && !from.getContent().isEmpty()) {
            this.setContent(from.getContent());
        }
        if (from.getViewQty() != null) {
            this.setViewQty(from.getViewQty());
        }
        if (from.getLikeQty() != null) {
            this.setLikeQty(from.getLikeQty());
        }
        if (from.getLikeRecord() != null){
            this.setLikeRecord(from.getLikeRecord());
        }
        if (from.getTotalComment() != null){
            this.setTotalComment(from.getTotalComment());
        }
        IBase.super.copyFields(from);
    }
}
