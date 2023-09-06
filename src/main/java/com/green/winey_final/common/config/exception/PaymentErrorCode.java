package com.green.winey_final.common.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {

    NO_PRODUCT_IN_CART(HttpStatus.BAD_REQUEST, "카트에 담긴 상품이 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
