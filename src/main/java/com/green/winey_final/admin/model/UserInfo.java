package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfo {
    private Long userId;
    private String email;
    private String unm;
    private int sumOrderPrice;
    private int orderCount;

    @QueryProjection
    public UserInfo(Long userId, String email, String unm, int sumOrderPrice, int orderCount) {
        this.userId = userId;
        this.email = email;
        this.unm = unm;
        this.sumOrderPrice = sumOrderPrice;
        this.orderCount = orderCount;
    }
}
