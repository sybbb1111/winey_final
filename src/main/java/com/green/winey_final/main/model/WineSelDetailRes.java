package com.green.winey_final.main.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WineSelDetailRes {
    private Long categoryId;
    private Long bigCategoryId;
    private Long countryId;
    private String text;
    private int sort;
    private int price;
    private int maxPage;
    private int startIdx;
    private int isMore;
    private int page;
    private int row;
    private List<WineVo> wineList;
}