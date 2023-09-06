package com.green.winey_final.admin.model;

import lombok.Data;

@Data
public class SelListDto {
    private int startIdx;
    private int page;
    private int row;

    private String type; //정렬용 type
    private String type2; //검색용 type
    private String sort; //asc, desc
    private String str; //검색어
}
