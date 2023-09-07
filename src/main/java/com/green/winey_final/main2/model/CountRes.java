package com.green.winey_final.main2.model;

import com.green.winey_final.search.model.WineVo;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CountRes {
    private int count;
    private List<WineTotalVo2> wineList;
}
