package com.green.winey_final.detail.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class WineVo4 {
    private WineDetailVo wineDetailVo;
    private List<String> selPairing;
    private List<String> selReview;
    private List<String> selAroma;
    private Long Level;
    private SelSale selSale;

}
