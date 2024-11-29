package com.melly.myweb.board.comment;

import com.melly.myweb.board.commentlike.CommentLikeDto;
import com.melly.myweb.board.commentlike.ICommentLikeMybatisMapper;
import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.exception.IdNotFoundException;
import com.melly.myweb.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardCommentServiceImpl implements IBoardCommentService{
    @Autowired
    private IBoardCommentMybatisMapper boardCommentMybatisMapper;

    @Autowired
    private ICommentLikeMybatisMapper commentLikeMybatisMapper;

    @Override
    public BoardCommentDto insert(CUDInfoDto cudInfoDto, BoardCommentDto boardCommentDto) {
        if( cudInfoDto == null || boardCommentDto == null ){
            return null;
        }
        BoardCommentDto insert = BoardCommentDto.builder().build();
        insert.copyFields(boardCommentDto);
        cudInfoDto.setCreateInfo(insert);
        this.boardCommentMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public BoardCommentDto update(CUDInfoDto cudInfoDto, BoardCommentDto boardCommentDto) {
        if( cudInfoDto == null || boardCommentDto == null){
            return null;
        }
        BoardCommentDto update = BoardCommentDto.builder().build();
        update.copyFields(boardCommentDto);
        cudInfoDto.setUpdateInfo(update);
        this.boardCommentMybatisMapper.update(update);
        return update;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto cudInfoDto, BoardCommentDto boardCommentDto) {
        if( cudInfoDto == null || boardCommentDto == null ){
            return null;
        }
        BoardCommentDto delete = BoardCommentDto.builder().build();
        delete.copyFields(boardCommentDto);
        delete.setDeleteFlag(true);
        cudInfoDto.setDeleteInfo(delete);
        this.boardCommentMybatisMapper.updateDeleteFlag(delete);
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if( id == null || id <= 0 ){
            return false;
        }
        this.boardCommentMybatisMapper.findById(id);
        return true;
    }

    @Override
    public BoardCommentDto findById(Long id) {
        if( id == null || id <= 0 ){
            return null;
        }
        BoardCommentDto find = this.boardCommentMybatisMapper.findById(id);
        if ( find == null ) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        return find;
    }

    @Override
    public List<BoardCommentDto> findAllByBoardId(IUser loginUser, SearchBoardCommentDto searchBoardCommentDto) {
        if( searchBoardCommentDto == null ){
            return List.of();
        }
        searchBoardCommentDto.settingValues();
        searchBoardCommentDto.setCommentTbl(new BoardCommentDto().getTbl());
        searchBoardCommentDto.setCreateId(loginUser.getId());

        List<BoardCommentDto> list = this.boardCommentMybatisMapper.findAllByBoardId(searchBoardCommentDto);
//        List<IBoardComment> result = this.getInterfaceList(list);
        return list;
    }

    @Override
    public Integer countAllByBoardId(SearchQueryDto searchQueryDto) {
        if( searchQueryDto == null ){
            return 0;
        }
        Integer total = this.boardCommentMybatisMapper.countAllByBoardId(searchQueryDto);
        return total;
    }

    @Override
    @Transactional
    public void addLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if( cudInfoDto == null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ){
            return;
        }
        CommentLikeDto commentLikeDto = CommentLikeDto.builder()
                .commentId(id)
                .createId(cudInfoDto.getLoginUser().getId())
                .commentTbl(new BoardCommentDto().getTbl()).build();

        Integer count = this.commentLikeMybatisMapper.countByCommentLike(commentLikeDto);
        if( count > 0 ){
            return;
        }
        this.commentLikeMybatisMapper.insert(commentLikeDto);
        this.boardCommentMybatisMapper.addLikeQty(id);
    }

    @Override
    @Transactional
    public void subLikeQty(CUDInfoDto cudInfoDto, Long id) {
        if( cudInfoDto== null || cudInfoDto.getLoginUser() == null || id == null || id <= 0 ){
            return;
        }
        CommentLikeDto commentLikeDto = CommentLikeDto.builder()
                .commentId(id)
                .createId(cudInfoDto.getLoginUser().getId())
                .commentTbl(new BoardCommentDto().getTbl()).build();

        Integer count = this.commentLikeMybatisMapper.countByCommentLike(commentLikeDto);
        if( count < 1 ){
            return;
        }
        this.commentLikeMybatisMapper.deleteByCommentLike(commentLikeDto);
        this.boardCommentMybatisMapper.subLikeQty(id);
    }

}
