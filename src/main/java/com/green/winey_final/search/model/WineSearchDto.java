package com.green.winey_final.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WineSearchDto {
    private Long categoryId;
    private Long bigCategoryId;
    private Long countryId;
    private String text;
    private int sort;
    private int price;
    private int startIdx;
    private int page;
    private int row;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long product_id;
}