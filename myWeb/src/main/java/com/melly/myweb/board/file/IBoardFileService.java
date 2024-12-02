package com.melly.myweb.board.file;

import com.melly.myweb.board.IBoard;
import com.melly.myweb.commons.inif.IServiceCRUD;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBoardFileService extends IServiceCRUD<BoardFileDto> {
    Boolean insertFiles(IBoard boardDto, List<MultipartFile> files);
    Boolean updateFiles(List<BoardFileDto> boardFileDtoList);

    List<IBoardFile> findAllByTblBoardId(IBoardFile boardFile);

    byte[] getBytesFromFile(IBoardFile file);
}
