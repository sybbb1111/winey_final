package com.green.winey_final.main.model;

import com.green.winey_final.search.model.WineVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WineCategoryDetailRes {
    private Long categoryId;
    private Long bigCategoryId;
    private Long countryId;
    private int sort;
    private int price;
    private int maxPage;
    private int startIdx;
    private int isMore;
    private int page;
    private int row;
    private int count;
    private List<WineVo> wineList;
}