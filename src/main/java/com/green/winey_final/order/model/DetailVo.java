package com.green.winey_final.order.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DetailVo {
    private List<OrderDetailVo1> vo1;
    private OrderDetailVo2 vo2;
}
