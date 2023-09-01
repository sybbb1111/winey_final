package com.green.winey_final.admin.model;

import lombok.Data;

@Data
public class ProductAromaDto {
    private int aromaId;
    private int productId;

    private int flower;
    private int plant;
    private int fruit;
    private int spicy;
    private int earth;
    private int oak;
    private int nuts;

    public ProductAromaDto(ProductAromaParam param) {
        this.flower = param.getFlower();
        this.plant = param.getPlant();
        this.fruit = param.getFruit();
        this.spicy = param.getSpicy();
        this.earth = param.getEarth();
        this.oak = param.getOak();
        this.nuts = param.getNuts();
    }
}
