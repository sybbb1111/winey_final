package com.green.winey_final.admin.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductList {
    private PageDto page;
    private List<ProductVo> productList;
}
