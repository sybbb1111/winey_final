package com.green.winey_final.admin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageDto {
    private int page; //현재 페이지 번호
    private int row; //페이지 당 표시할 개수
    private int startIdx;
    private int totalRecordCount; //전체 데이터 수
    private int totalPage; //전체 페이지 수
    private int pageSize; //화면 하단에 출력할 페이지 크기 ex) 5는 1~5까지의 페이지가 보임
    private boolean prev; //이전 페이지 표시 여부
    private boolean next; //다음 페이지 표시 여부
    private int startPage; //화면에 보이는 시작 번호
    private int endPage; //화면에 보이는  끝 번호

    public PageDto(int totalRecordCount, int page, int row) {
        this.page = page;
        this.row = row;
        this.pageSize = 10;
        if(totalRecordCount>0) {
            this.totalRecordCount = totalRecordCount;
            calculation(page, row);
        }
    }

    private void calculation(int page, int row) {

        // 전체 페이지 수 계산
        totalPage = ((totalRecordCount - 1) / row) + 1;

        // 현재 페이지 번호가 전체 페이지 수보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장
        if (page > totalPage) {
            this.page = totalPage;
        }

        // 첫 페이지 번호 계산
        if(page == 1) {
            startPage = 1;
        } else {
            startPage = (page - 1) / pageSize * pageSize + 1;
        }

        // 끝 페이지 번호 계산
        endPage = startPage + pageSize - 1;

        // 끝 페이지가 전체 페이지 수보다 큰 경우, 끝 페이지 전체 페이지 수 저장
        if (endPage > totalPage) {
            endPage = totalPage;
        }

        // LIMIT 시작 위치 계산
        startIdx = (page - 1) * row;

        // 이전 페이지 존재 여부 확인
        prev = startPage != 1;

        // 다음 페이지 존재 여부 확인
        next = (endPage * row) < totalRecordCount;
    }
}

