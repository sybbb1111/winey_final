package com.green.winey_final.detail.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SelAroma {
    private Long productId;
    private List<String> aromaNm;

//    private int flower;
//    private int plant;
//    private int fruit;
//    private int spicy;
//    private int earth;
//    private int oak;
//    private int nuts;

}
