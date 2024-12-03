package com.melly.myweb.board;

import com.melly.myweb.board.file.BoardFileDto;
import com.melly.myweb.board.file.IBoardFile;
import com.melly.myweb.board.file.IBoardFileMybatisMapper;
import com.melly.myweb.board.file.IBoardFileService;
import com.melly.myweb.board.like.BoardLikeDto;
import com.melly.myweb.board.like.IBoardLikeMybatisMapper;
import com.melly.myweb.commons.dto.SearchQueryDto;
import com.melly.myweb.commons.exception.IdNotFoundException;

import com.melly.myweb.commons.dto.CUDInfoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class BoardServiceImpl implements IBoardService {
    @Autowired
    private IBoardMybatisMapper boardMybatisMapper;
    @Autowired
    private IBoardLikeMybatisMapper boardLikeMybatisMapper;
    @Autowired
    private IBoardFileMybatisMapper boardFileMybatisMapper;
    @Autowired
    private IBoardFileService boardFileService;

    @Transactional
    public BoardDto insert(CUDInfoDto info, BoardDto boardDto, List<MultipartFile> files) throws RuntimeException {
        if ( info == null || boardDto == null ) {
            return null;
        }
        BoardDto insert = BoardDto.builder().build();
        insert.copyFields(boardDto);
        info.setCreateInfo(insert);
        this.boardMybatisMapper.insert(insert);
        this.boardFileService.insertFiles(insert, files);
        return insert;
    }

    @Transactional
    public BoardDto update(CUDInfoDto info, BoardDto boardDto, List<BoardFileDto> boardFileDtoList, List<MultipartFile> files) throws RuntimeException {
        if ( info == null || boardDto == null ) {
            return null;
        }
        BoardDto update = BoardDto.builder().build();
        update.copyFields(boardDto);
        info.setUpdateInfo(update);
        this.boardMybatisMapper.update(update);
        this.boardFileService.updateFiles(boardFileDtoList);
        this.boardFileService.insertFiles(update,files);
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
        BoardFileDto boardFileDto= BoardFileDto.builder()
                .tbl(boardDto.getTbl())
                .boardId(boardDto.getId())
                .build();
        List<BoardFileDto> boardFileDtoList = this.boardFileMybatisMapper.findAllByTblBoardId(boardFileDto);
        for( BoardFileDto result : boardFileDtoList){
            result.setDeleteFlag(true);
            this.boardFileMybatisMapper.updateDeleteFlag(result);
            // this.fileCtrlService.deleteFile(sbFileDto.getTbl(), sbFileDto.getUniqName(), sbFileDto.getFileType());
        }
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

//    @Override
//    public List<BoardDto> findGetAll() {
//        List<BoardDto> list = this.boardMybatisMapper.findGetAll();
//        return list;
//    }

    @Override
    public List<BoardDto> findAllByNameContains(SearchQueryDto searchQueryDto) {
        if ( searchQueryDto == null ) {
            return List.of();
        }
        searchQueryDto.settingValues();
        List<BoardDto> list = this.boardMybatisMapper.findAllByNameContains(searchQueryDto);
        List<BoardDto> result = list.stream()
                .peek(x -> {
                    String totalComment = x.getTotalComment();
                    x.setTotalComment(totalComment == null || totalComment.equals("0")
                            ? "[0]"
                            : "[" + totalComment + "]");
                })
                .toList();
        return result;
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
    public void addViewQty(Long id,Long userId) {
        if ( id == null || id <= 0 ) {
            return;
        }

        // 본인이 작성한 글은 조회수 증가 X
        BoardDto find = this.boardMybatisMapper.findById(id);
        if( find.getCreateId().equals(userId) ){
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


    @Override
    public BoardDto insert(CUDInfoDto cudInfoDto, BoardDto dto) {
        return null;
    }

    @Override
    public BoardDto update(CUDInfoDto cudInfoDto, BoardDto dto) {
        return null;
    }
}
