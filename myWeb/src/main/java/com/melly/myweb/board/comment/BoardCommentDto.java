package com.melly.myweb.board.comment;

import com.melly.myweb.commons.dto.BaseDto;
import jakarta.validation.constraints.Size;
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
public class BoardCommentDto extends BaseDto implements IBoardComment {
    private Long id;

    @Size(min = 1, max = 1000, message = "댓글은 1~1000자 입니다.")
    private String comment;

    private Integer likeQty;
    private Long boardId;
    private Long commentId;

    public String getTbl(){
        return "boardcomment";
    }
    private Boolean commentLikeRecoder;
}
