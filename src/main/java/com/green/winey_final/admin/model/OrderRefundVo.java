package com.green.winey_final.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderRefundVo {
    private Long refundId;
    private Long orderId;
    private String refundReason;
    private int refundYn;
    private String refundDate;
}
