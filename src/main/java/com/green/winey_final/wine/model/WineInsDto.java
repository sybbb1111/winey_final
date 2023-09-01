package com.green.winey_final.wine.model;

import lombok.Data;

@Data
public class WineInsDto {
    private String location;
    private String bottle_large;
//    private String bottle_medium;
//    private String bottle_small;
    private int id;
    private String name;
    private double price;
    private int type_id;
    private int acidity;
    private int fizziness;
    private int tannin;
    private int sweetness;


}
