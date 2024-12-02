package com.melly.myweb.board.file;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BoardFileDto implements IBoardFile{
    private Long id;
    private String name;
    private Integer ord;        // 항목의 순서를 지정
    private String fileType;    // .jpg 등
    private String uniqName;    // 고유 이름
    private Long length;        // 파일 크기(byte)
    private String description;
    private String tbl;
    private Long boardId;
    private Boolean deleteFlag;
}
