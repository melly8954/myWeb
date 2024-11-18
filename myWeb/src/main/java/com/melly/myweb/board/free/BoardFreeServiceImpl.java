package com.melly.myweb.board.free;

import com.melly.myweb.board.like.IBoardLikeMybatisMapper;
import com.melly.myweb.board.like.IBoardLikeService;
import com.melly.myweb.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardFreeServiceImpl implements IBoardFreeService{
    @Autowired
    private IBoardFreeMybatisMapper boardFreeMybatisMapper;

    @Autowired
    private IBoardLikeMybatisMapper boardLikeMybatisMapper;


    @Override
    public IBoardFree insert(BoardFreeDto boardFreeDto, IUser user) {
        if( boardFreeDto == null ){
            return null;
        }
        BoardFreeDto insert = BoardFreeDto.builder().build();
        insert.copyFields(boardFreeDto);
        insert.setCreateId(user.getId());
        this.boardFreeMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public IBoardFree update(BoardFreeDto boardFreeDto) {
        if( boardFreeDto == null || boardFreeDto.getId() == null || boardFreeDto.getId() <= 0 ){
            return null;
        }
        boardFreeDto.setUpdateDt(boardFreeDto.getSystemDt());

        BoardFreeDto update = BoardFreeDto.builder().build();
        update.copyFields(boardFreeDto);
        this.boardFreeMybatisMapper.update(update);
        return update;
    }

    @Override
    public Boolean delete(Long id) {
        if( id == null || id < 0 ){
            return false;
        }
        this.boardFreeMybatisMapper.delete(id);
        return true;
    }

    @Override
    public IBoardFree findById(Long id) {
        if( id == null || id < 0 ){
            return null;
        }
        IBoardFree find = this.boardFreeMybatisMapper.findById(id);
        return find;
    }

    @Override
    public List<BoardFreeDto> findAllByNameContains(SearchBoardDto searchBoardDto) {
        return List.of();
    }

    @Override
    public Integer countAllByNameContains(SearchBoardDto searchBoardDto) {
        return 0;
    }

    @Override
    public void addViewQty(Long id, IUser user) throws Exception {

    }

    @Override
    public void addLikeQty(Long id, IUser user) throws Exception {

    }

    @Override
    public void subLikeQty(Long id, IUser user) throws Exception {

    }
}
