package com.melly.myweb.board.like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BoardLikeDto implements IBoardLike {
    private Long id;
    private Long createId;
    private Long boardId;
    private String tbl;
}
