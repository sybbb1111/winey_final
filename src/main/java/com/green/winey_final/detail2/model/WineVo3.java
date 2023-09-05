package com.green.winey_final.detail2.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class WineVo3 {
    private WineDetailVo2 wineDetailVo;
    private List<String> selPairing;
    private List<String> selReview;
    private List<String> selAroma;
    private int Level;
    private SelSale2 selSale;

}
