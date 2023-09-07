package com.green.winey_final.main2.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CountFoodRes {
    private int count;
    private List<WineFoodVo2> wineList;
}
