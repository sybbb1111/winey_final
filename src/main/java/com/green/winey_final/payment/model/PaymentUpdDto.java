package com.green.winey_final.payment.model;

import lombok.Data;

@Data
public class PaymentUpdDto {
    private long orderId;
    private int orderStatus;
}
