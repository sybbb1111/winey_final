package com.green.winey_final.main.model;

import lombok.Data;

@Data
public class WineSelDto {
    private Long productId;
    private int startIdx;
    private int page;
    private int row;

}
