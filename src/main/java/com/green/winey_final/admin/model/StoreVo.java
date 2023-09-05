package com.green.winey_final.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreVo {
    private Long storeId;
    private Long regionNmId;
    private String nm;
    private String tel;
    private String address;

}
