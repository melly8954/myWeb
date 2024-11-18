package com.melly.myweb.board.free;

import com.melly.myweb.board.common.IBoardBase;

public interface IBoardFree extends IBoardBase {
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

    String getCountComment();
    void setCountComment(String countComment);

    default void copyFields(IBoardFree from) {
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
        if (from.getCreateDt() != null && !from.getCreateDt().isEmpty()) {
            this.setCreateDt(from.getCreateDt());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getUpdateDt() != null && !from.getUpdateDt().isEmpty()) {
            this.setUpdateDt(from.getUpdateDt());
        }
        if (from.getDeleteYn() != null) {
            this.setDeleteYn(from.getDeleteYn());
        }
        if (from.getCreateName() != null) {
            this.setCreateName(from.getCreateName());
        }

    }
}
