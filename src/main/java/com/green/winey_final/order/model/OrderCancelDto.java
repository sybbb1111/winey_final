package com.green.winey_final.order.model;

import lombok.Data;

@Data
public class OrderCancelDto {
    private Long orderId;
    private Long userId;

}
