package com.green.winey_final.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DetailVo {
    private List<OrderDetailVo1> vo1;
    private OrderDetailVo2 vo2;
}
