package com.green.winey_final.admin.model;

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
}
