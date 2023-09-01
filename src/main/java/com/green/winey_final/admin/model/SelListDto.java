package com.green.winey_final.admin.model;

import lombok.Data;

@Data
public class SelListDto {
    private int startIdx;
    private int page;
    private int row;

    private String type;
    private String sort; //asc, desc
}
