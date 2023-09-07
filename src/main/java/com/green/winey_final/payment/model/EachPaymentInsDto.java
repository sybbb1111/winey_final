package com.green.winey_final.payment.model;

import lombok.Data;

@Data
public class EachPaymentInsDto {
    private long productId; //제품pk
    private long storeId; //매장pk
    private String pickupTime; //픽업날짜
    private int quantity; //수량
}
