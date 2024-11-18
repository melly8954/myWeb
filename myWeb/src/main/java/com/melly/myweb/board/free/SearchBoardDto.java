package com.melly.myweb.board.free;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class SearchBoardDto {
    private String orderByWord;     // 정렬할 컬럼
    private String searchName;      // 검색어
    private String searchType;      // 검색분류(제목,작성자)
    private String sortColumn;
    private String sortAscDesc;     // 오름차순,내림차순
    private Integer rowsOnePage;    // 1페이지에 들어갈 수 있는 행의 갯수
    private Integer firstIndex;     // 어디서부터 가져올지

    private Integer page;
    private Integer total;
    private List<?> dataList;

    public Integer getFirstIndex(){
        return (this.page -1) * this.rowsOnePage;
    }

    public void settingValues(){
        this.setOrderByWord( (this.getSortColumn() != null ? this.getSortColumn() : "id")
            + " " + (this.getSortAscDesc() != null ? this.getSortAscDesc() : "DESC") );

        // SQL select 문장의 ORDER BY 구문을 만들어 주는 역할
        if ( this.getSearchType() == null ) {
            this.setSearchType("title");
        }
        if ( this.getRowsOnePage() == null ) {
            // 한 페이지당 보여주는 행의 갯수
            this.setRowsOnePage(10);
        }
        if ( this.getPage() == null || this.getPage() <= 0 ) {
            this.setPage(1);
        }

    }
}
