package com.melly.myweb.board.free;

import com.melly.myweb.user.IUser;

import java.util.List;

public interface IBoardFreeService {

    IBoardFree insert(BoardFreeDto boardFreeDto, IUser user);

    IBoardFree update(BoardFreeDto boardFreeDto);

    Boolean delete(Long id);

    IBoardFree findById(Long id);

    List<BoardFreeDto> findAllByNameContains(SearchBoardDto searchBoardDto);

    Integer countAllByNameContains(SearchBoardDto searchBoardDto);

    void addViewQty(Long id, IUser user) throws Exception;

    void addLikeQty(Long id, IUser user) throws Exception;

    void subLikeQty(Long id, IUser user) throws Exception;
}
