package com.green.winey_final.main2.model;

import lombok.Data;

@Data
public class WineSelByCountryDto2 {
    private Long countryId;
    private int startIdx;
    private int page;
    private int row;
}
