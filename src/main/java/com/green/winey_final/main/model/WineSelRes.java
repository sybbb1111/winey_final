package com.green.winey_final.main.model;

import com.green.winey_final.common.entity.ProductEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WineSelRes {
    private Long productId;
    private int startIdx;
    private int size;
    List<ProductEntity> list;
}
