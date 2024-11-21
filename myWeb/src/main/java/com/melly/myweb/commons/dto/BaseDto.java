package com.melly.myweb.commons.dto;

import com.melly.myweb.board.IBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseDto implements IBase{
    private Long createId;
    private String createDt;
    private String createName;
    private Long updateId;
    private String updateDt;
    private String updateName;
    private Long deleteId;
    private String deleteDt;
    private String deleteName;
    private Boolean deleteFlag;
}
