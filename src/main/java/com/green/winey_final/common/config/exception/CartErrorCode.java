package com.green.winey_final.common.config.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CartErrorCode implements ErrorCode {

    NO_CART(HttpStatus.BAD_REQUEST, "해당하는 카드가 없습니다."),
    QUANTITY_OVER(HttpStatus.BAD_REQUEST, "카트에 담을 수 있는 수량을 초과하였습니다."),
    QUANTITY_VALUE_OVER(HttpStatus.BAD_REQUEST, "카트에 담을 수 있는 수량값이 5를 초과할 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
