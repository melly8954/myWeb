package com.melly.myweb.board.free;

import com.melly.myweb.board.common.BoardBaseDto;
import jakarta.validation.constraints.Size;
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
public class BoardFreeDto extends BoardBaseDto implements IBoardFree {
    private Long id;

    @Size(min=2, max=100, message = "제목은 2~100자 사이로 입력하셔야 합니다.")
    private String title;

    @Size(min=2, max=1000, message = "본문은 2~1000자 사이로 입력하셔야 합니다.")
    private String content;

    private Integer viewQty;
    private Integer likeQty;
    private String countComment;

    public String getTbl() {
        return "free";
    }
}
