package com.green.winey_final.main.model;

import lombok.Data;

@Data
public class WineSelByCountryDto {
    private Long countryId;
    private int startIdx;
    private int page;
    private int row;
}
