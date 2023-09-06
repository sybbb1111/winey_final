package com.green.winey_final.common.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PaymentErrorCode implements ErrorCode {
    NO_ORDER(HttpStatus.BAD_REQUEST, "해당하는 주문 번호가 없습니다."),
    NO_PRODUCT_IN_CART(HttpStatus.BAD_REQUEST, "카트에 담긴 상품이 없습니다."),
    NO_PRODUCT(HttpStatus.BAD_REQUEST, "해당하는 상품이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
