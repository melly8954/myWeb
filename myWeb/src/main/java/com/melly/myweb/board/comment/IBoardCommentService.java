package com.melly.myweb.board.comment;

import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.inif.IServiceCRUD;
import com.melly.myweb.user.IUser;

import java.util.List;

public interface IBoardCommentService extends IServiceCRUD<BoardCommentDto> {
    List<BoardCommentDto> findAllByBoardId(IUser loginUser, SearchBoardCommentDto searchBoardCommentDto);
    Integer countAllByBoardId(SearchQueryDto searchQueryDto);

    void addLikeQty(CUDInfoDto cudInfoDto, Long id);
    void subLikeQty(CUDInfoDto cudInfoDto, Long id);

}
