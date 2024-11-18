package com.melly.myweb.board.free;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IBoardFreeMybatisMapper {

    // 게시판 등록
    void insert(BoardFreeDto boardFreeDto);

    // 게시글 수정
    void update(BoardFreeDto boardFreeDto);

    // 게시글 삭제
    void delete(Long id);

    // 게시글 상세정보 조회
    BoardFreeDto findById(Long id);

    // 게시글 리스트 조회
    List<BoardFreeDto> findAllByNameContains(SearchBoardDto searchBoardDto);

    // 게시글 수 카운트
    Integer countAllByNameContains(SearchBoardDto searchBoardDto);

    // 게시글 조회수 증가
    void addViewQty(Long id);

    // 게시글 좋아요 누를 경우
    void addLikeQty(Long id);

    // 게시글 좋아요 취소
    void subLikeQty(Long id);
}
