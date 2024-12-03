package com.melly.myweb.board.file;

import com.melly.myweb.board.IBoard;
import com.melly.myweb.commons.dto.CUDInfoDto;
import com.melly.myweb.commons.exception.IdNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class BoardFileServiceImpl implements IBoardFileService {
    @Autowired
    private IBoardFileMybatisMapper boardFileMybatisMapper;

    @Autowired
    private FileCtrlService fileCtrlService;

    @Override
    public Boolean insertFiles(IBoard boardDto, List<MultipartFile> files) {
        if(boardDto == null || files == null || files.isEmpty()) {
            return false;
        }
        int ord = 0;
        try{
            for(MultipartFile file : files) {
                BoardFileDto insert = BoardFileDto.builder()
                        .name(file.getOriginalFilename())
                        .ord(ord++)
                        .fileType(this.getFileType(Objects.requireNonNull(file.getOriginalFilename())))
                        .uniqName(UUID.randomUUID().toString())
                        .length(file.getSize())
                        .tbl(boardDto.getTbl())
                        .boardId(boardDto.getId())
                        .build();

                this.boardFileMybatisMapper.insert(insert);
                this.fileCtrlService.saveFile(file,insert.getTbl(),insert.getUniqName() + insert.getFileType());
            }
            return true;
        }catch (Exception ex){
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }
    private String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }


    @Override
    public Boolean updateFiles(List<BoardFileDto> boardFileDtoList) {
        for ( BoardFileDto boardFileDto : boardFileDtoList ) {
            if (boardFileDto.getDeleteFlag()) {
                this.boardFileMybatisMapper.updateDeleteFlag(boardFileDto);
            }
        }
        return true;
    }

    @Override
    public Boolean deleteById(Long id) {
        if (id == null || id <= 0) {
            return false;
        }
        this.boardFileMybatisMapper.deleteById(id);
        return true;
    }

    @Override
    public List<IBoardFile> findAllByTblBoardId(IBoardFile boardFile) {
        if (boardFile == null) {
            return List.of();
        }
        BoardFileDto boardFileDto = BoardFileDto.builder().build();
        boardFileDto.copyFields(boardFile);

        List<BoardFileDto> list = this.boardFileMybatisMapper.findAllByTblBoardId(boardFileDto);
        List<IBoardFile> result = this.getInterfaceList(list);
        return result;
    }
    private List<IBoardFile> getInterfaceList(List<BoardFileDto> list) {
        if (list == null) {
            return List.of();
        }
        List<IBoardFile> result = list.stream().map(x -> (IBoardFile) x).toList();
        return result;
    }


    @Override
    public byte[] getBytesFromFile(IBoardFile file) {
        if ( file == null ) {
            return new byte[0];
        }
        byte[] result = this.fileCtrlService.downloadFile(file.getTbl(), file.getUniqName(), file.getFileType());
        return result;
    }

    @Override
    public BoardFileDto findById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        BoardFileDto find = this.boardFileMybatisMapper.findById(id);
        if (find == null) {
            throw new IdNotFoundException(String.format("Error : not found id = %d !", id));
        }
        return find;
    }




    @Override
    public BoardFileDto insert(CUDInfoDto cudInfoDto, BoardFileDto dto) {
        return null;
    }

    @Override
    public BoardFileDto update(CUDInfoDto cudInfoDto, BoardFileDto dto) {
        return null;
    }

    @Override
    public Boolean updateDeleteFlag(CUDInfoDto cudInfoDto, BoardFileDto dto) {
        return null;
    }


}
