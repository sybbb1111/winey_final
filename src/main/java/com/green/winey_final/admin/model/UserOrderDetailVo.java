package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@Builder
@Data
public class UserOrderDetailVo {
    private Long orderId;
    private String orderDate;
    private String nmKor;
    private int price; //최종결제금액
    private String storeNm; //픽업지점이름
    private int orderStatus; //주문상태
    private int count; //order_detail 행 수

    @QueryProjection
    public UserOrderDetailVo(Long orderId, String orderDate, String nmKor, int price, String storeNm, int orderStatus, int count) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.nmKor = nmKor;
        this.price = price;
        this.storeNm = storeNm;
        this.orderStatus = orderStatus;
        this.count = count;
    }
}
