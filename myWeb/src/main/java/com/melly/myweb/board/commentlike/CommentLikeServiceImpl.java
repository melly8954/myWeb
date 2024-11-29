package com.melly.myweb.board.commentlike;

import com.melly.myweb.commons.dto.CUDInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements ICommentLikeService {
    @Autowired
    private ICommentLikeMybatisMapper commentLikeMybatisMapper;

    @Override
    public Integer countCommentLike(CommentLikeDto commentLikeDto) {
        if(commentLikeDto == null || commentLikeDto.getCommentTbl() == null || commentLikeDto.getCommentTbl().isEmpty()
            || commentLikeDto.getCommentId() == null || commentLikeDto.getCommentId() <= 0
            || commentLikeDto.getCreateId() == null){
            return 0;
        }
        CommentLikeDto dto = CommentLikeDto.builder().build();
        dto.copyFields(commentLikeDto);
        Integer count = this.commentLikeMybatisMapper.countByCommentLike(dto);
        return count;
    }

    @Override
    public CommentLikeDto insert(CUDInfoDto cudInfoDto, CommentLikeDto commentLikeDto) {
        if(commentLikeDto == null || commentLikeDto.getCommentTbl() == null || commentLikeDto.getCommentTbl().isEmpty()
                || commentLikeDto.getCommentId() == null || commentLikeDto.getCommentId() <= 0
                || commentLikeDto.getCreateId() == null){
            return null;
        }
        CommentLikeDto insert = CommentLikeDto.builder().build();
        insert.copyFields(commentLikeDto);
        this.commentLikeMybatisMapper.insert(insert);
        return insert;
    }

    @Override
    public CommentLikeDto update(CUDInfoDto cudInfoDto, CommentLikeDto dto) {
        return null;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto cudInfoDto, CommentLikeDto dto) {
        return null;
    }

    @Override
    public Boolean deleteById(Long id) {
        return null;
    }

    @Override
    public CommentLikeDto findById(Long id) {
        return null;
    }
}
