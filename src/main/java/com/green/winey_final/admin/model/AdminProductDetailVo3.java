package com.green.winey_final.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class AdminProductDetailVo3 {
    private AdminProductDetailVo product;
    private List<Long> aroma;
    private List<Long> smallCategoryId;


}
