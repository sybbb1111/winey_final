package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
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

    @QueryProjection
    public StoreVo(Long storeId, Long regionNmId, String nm, String tel, String address) {
        this.storeId = storeId;
        this.regionNmId = regionNmId;
        this.nm = nm;
        this.tel = tel;
        this.address = address;
    }
}
