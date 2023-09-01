package com.green.winey_final.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserOrderDetailList {
    private PageDto page;
    private UserInfo userInfo;
    private List<UserOrderDetailVo> userOrderList;
}
