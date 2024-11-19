package com.melly.myweb.board.free;

import com.melly.myweb.board.like.BoardLikeDto;
import com.melly.myweb.board.like.IBoardLikeMybatisMapper;
import com.melly.myweb.board.like.IBoardLikeService;
import com.melly.myweb.user.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        if(searchBoardDto == null){
            return List.of();   // 빈 리스트 반환
        }
        searchBoardDto.settingValues();
        searchBoardDto.setFirstIndex(searchBoardDto.getFirstIndex());
        List<BoardFreeDto> list = this.boardFreeMybatisMapper.findAllByNameContains(searchBoardDto);

        // 댓글 수 포맷팅 (댓글이 "0"이면 빈 문자열, 아니면 [댓글 수] 형식)
        List<BoardFreeDto> result = list.stream()
                .map(x -> {
                    // countComment가 "0"일 경우 빈 문자열로 변경
                    if ("0".equals(x.getCountComment())) {
                        x.setCountComment("");
                    } else {
                        // 아니면 대괄호로 감싸기
                        x.setCountComment("[" + x.getCountComment() + "]");
                    }
                    return x;
                })
                .collect(Collectors.toList()); // 스트림 결과를 리스트로 수집
        return result;
    }

    @Override
    public Integer countAllByNameContains(SearchBoardDto searchBoardDto) {
        searchBoardDto.settingValues();
        return this.boardFreeMybatisMapper.countAllByNameContains(searchBoardDto);
    }

    @Override
    public void addViewQty(Long id, IUser user) throws Exception {
        if( id == null || id <= 0 ){
            return;
        }
        // 본인 작성글일 경우에는 조회수 증가x
        IBoardFree find = this.boardFreeMybatisMapper.findById(id);
        if(find.getCreateId().equals(user.getId())){
            return;
        }
        this.boardFreeMybatisMapper.addViewQty(id);
    }

    @Override
    public void addLikeQty(Long id, IUser user) throws Exception {
        if( id == null || id <= 0 || user == null ){
            return;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .createId(user.getId())
                .boardId(id)
                .tbl(new BoardFreeDto().getTbl())
                .build();

        Integer count = this.boardLikeMybatisMapper.countByLike(boardLikeDto);
        // 한 사람당 1번의 좋아요 기능 사용 즉, 0보다 큰 경우 이미 좋아요를 누른 경우라 return
        if( count > 0 ){
            return;
        }
        // 좋아요 테이블에 행 삽입
        this.boardLikeMybatisMapper.insert(boardLikeDto);
        // 자유게시판 테이블에 좋아요 수 + 1
        this.boardFreeMybatisMapper.addLikeQty(id);
    }

    @Override
    public void subLikeQty(Long id, IUser user) throws Exception {
        if( id == null || id <= 0 || user == null ){
            return;
        }
        BoardLikeDto boardLikeDto = BoardLikeDto.builder()
                .createId(user.getId())
                .boardId(id)
                .tbl(new BoardFreeDto().getTbl())
                .build();

        Integer count = this.boardLikeMybatisMapper.countByLike(boardLikeDto);
        if( count < 1 ){
            return;
        }
        // 좋아요 테이블에 행 삭제
        this.boardLikeMybatisMapper.delete(boardLikeDto);
        // 자유게시판 테이블에 좋아요 수 -1
        this.boardFreeMybatisMapper.subLikeQty(id);

    }
}
