package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
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

    @QueryProjection
    public OrderRefundVo(Long refundId, Long orderId, String refundReason, int refundYn, String refundDate) {
        this.refundId = refundId;
        this.orderId = orderId;
        this.refundReason = refundReason;
        this.refundYn = refundYn;
        this.refundDate = refundDate;
    }
}
