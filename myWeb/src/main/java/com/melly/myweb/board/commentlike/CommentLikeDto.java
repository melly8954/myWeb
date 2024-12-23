package com.melly.myweb.board.commentlike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikeDto implements ICommentLike{
    private Long id;
    private Long commentId;
    private Long createId;   // user_id
    private String commentTbl;

}
