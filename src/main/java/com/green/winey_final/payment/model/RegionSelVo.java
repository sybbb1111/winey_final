package com.green.winey_final.payment.model;

import lombok.Data;

@Data
public class RegionSelVo {
    private long regionNmId; //지역pk
    private String regionNm; //지역이름
    private long storeId; //매장pk
    private String nm; //매장 지점 이름
    private String address; //매장주소
}
