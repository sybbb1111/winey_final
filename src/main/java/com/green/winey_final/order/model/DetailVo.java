package com.green.winey_final.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DetailVo {
    private List<OrderDetailVo1> vo1;
    private String orderDate;
    private Integer payment;
    private LocalDateTime pickupTime;
    private Integer orderStatus;
    private Integer totalOrderPrice;
    private String storeNm;
}
