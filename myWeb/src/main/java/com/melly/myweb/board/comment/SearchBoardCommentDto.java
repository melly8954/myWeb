package com.melly.myweb.board.comment;

import com.melly.myweb.commons.dto.SearchQueryDto;
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
public class SearchBoardCommentDto extends SearchQueryDto{
    private String boardId;
    private String commnetTbl;
    private Long createId;
    private Long commentId;
}
