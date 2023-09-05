package com.green.winey_final.order2.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DetailVo3 {
    private List<OrderDetailVo12> vo1;
    private OrderDetailVo22 vo2;
}
