package com.green.winey_final.payment.model;

import lombok.Data;

@Data
public class PaymentInsDto {
    private long storeId; // 매장 pk 값
    private String pickupTime; // 픽업 날짜
}
