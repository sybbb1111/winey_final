package com.green.winey_final.main2.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WineSelDto2 {
    private int startIdx;
    private int page;
    private int row;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long product_id;
}
