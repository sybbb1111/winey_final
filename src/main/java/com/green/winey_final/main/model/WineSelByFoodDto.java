package com.green.winey_final.main.model;

import lombok.Data;

@Data
public class WineSelByFoodDto {
    private Long bigCategoryId;
    private int startIdx;
    private int page;
    private int row;
}
