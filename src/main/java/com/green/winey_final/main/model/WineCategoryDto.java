package com.green.winey_final.main.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WineCategoryDto {
    private Long categoryId;
    private Long bigCategoryId;
    private Long countryId;
    private int sort;
    private int price;
    private int startIdx;
    private int page;
    private int row;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long product_id;
}