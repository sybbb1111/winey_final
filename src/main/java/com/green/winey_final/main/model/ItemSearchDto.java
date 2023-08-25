package com.green.winey_final.main.model;

import lombok.Data;

@Data
public class ItemSearchDto {
    private Long iitemCategory;
    private String text;
    private int sort;
    private int price;
    private int startIdx;
    private int page;
    private int row;
}