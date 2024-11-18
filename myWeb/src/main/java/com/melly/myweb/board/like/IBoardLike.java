package com.melly.myweb.board.like;

public interface IBoardLike {
    Long getId();
    void setId(Long id);

    Long getCreateId();
    void setCreateId(Long createId);

    Long getBoardId();
    void setBoardId(Long boardId);

    String getTbl();
    void setTbl(String tbl);

    default void copyFields(IBoardLike from) {
        if (from == null) {
            return;
        }
        if (from.getId() != null) {
            this.setId(from.getId());
        }
        if (from.getTbl() != null && !from.getTbl().isEmpty()) {
            this.setTbl(from.getTbl());
        }
        if (from.getCreateId() != null) {
            this.setCreateId(from.getCreateId());
        }
        if (from.getBoardId() != null) {
            this.setBoardId(from.getBoardId());
        }
    }
}
