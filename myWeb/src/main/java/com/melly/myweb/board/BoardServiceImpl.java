package com.melly.myweb.board;

import com.melly.myweb.board.like.BoardLikeDto;
import com.melly.myweb.board.like.IBoardLikeMybatisMapper;
import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.exception.IdNotFoundException;

import com.melly.myweb.commons.dto.CUDInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements IBoardService {
    @Autowired
    private IBoardMybatisMapper boardMybatisMapper;

    @Autowired
    private IBoardLikeMybatisMapper boardLikeMybatisMapper;

    @Override
    public BoardDto insert(CUDInfoDto info, BoardDto boardDto) throws RuntimeException {
        if ( info == null || boardDto == null ) {
            return null;
        }
        BoardDto insert = BoardDto.builder().build();
        insert.copyFields(boardDto);
        info.setCreateInfo(insert);
        this.boardMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public BoardDto update(CUDInfoDto info, BoardDto boardDto) throws RuntimeException {
        if ( info == null || boardDto == null ) {
            return null;
        }
        BoardDto update = BoardDto.builder().build();
        update.copyFields(boardDto);
        info.setUpdateInfo(update);
        this.boardMybatisMapper.update(update);
        return update;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto info, BoardDto boardDto) {
        if ( info == null || boardDto == null ) {
            return false;
        }
        BoardDto delete = BoardDto.builder().build();
        delete.copyFields(boardDto);
        info.setDeleteInfo(delete);
        this.boardMybatisMapper.updateDeleteFlag(delete);
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if ( id == null || id <= 0 ) {
            return false;
        }
        this.boardMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public BoardDto findById(Long id) {
        if ( id == null || id <= 0 ) {
            return null;
        }
        BoardDto find = this.boardMybatisMapper.findById(id);
        if ( find == null ) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        return find;
    }

    @Override
    public List<BoardDto> findAllByNameContains(SearchQueryDto searchQueryDto) {
        if ( searchQueryDto == null ) {
            return List.of();
        }
        searchQueryDto.settingValues();
        List<BoardDto> list = this.boardMybatisMapper.findAllByNameContains(searchQueryDto);
        return list;
    }

    @Override
    public Integer countAllByNameContains(SearchQueryDto searchQueryDto) {
        if ( searchQueryDto == null ) {
            return 0;
        }
        Integer count = this.boardMybatisMapper.countAllByNameContains(searchQueryDto);
        return count;
    }

    @Override
    public void addViewQty(Long id) {
        if ( id == null || id <= 0 ) {
            return;
        }
        this.boardMybatisMapper.addViewQty(id);
    }

    @Override
    @Transactional
    public void addLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if ( cudInfoDto == null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ) {
            return;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .tbl(new BoardDto().getTbl())
                .createId(cudInfoDto.getLoginUser().getId())
                .boardId(id)
                .build();

        Integer count = this.boardLikeMybatisMapper.countByLike(boardLikeDto);
        if ( count > 0 ) {
            return;
        }
        this.boardLikeMybatisMapper.insert(boardLikeDto);
        this.boardMybatisMapper.addLikeQty(id);
    }

    @Override
    @Transactional
    public void subLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if ( cudInfoDto == null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ) {
            return;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .tbl(new BoardDto().getTbl())
                .createId(cudInfoDto.getLoginUser().getId())
                .boardId(id)
                .build();

        Integer count = this.boardLikeMybatisMapper.countByLike(boardLikeDto);
        if ( count < 1 ) {
            return;
        }
        this.boardLikeMybatisMapper.delete(boardLikeDto);
        this.boardMybatisMapper.subLikeQty(id);
    }





//    private List<IBoard> getInterfaceList(List<BoardDto> list) {
//        if( list == null ) {
//            return List.of();
//        }
//        List<IBoard> result = list.stream().map(item -> (IBoard)item).toList();
//        return result;
//    }
}
