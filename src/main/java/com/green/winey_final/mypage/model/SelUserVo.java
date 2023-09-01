package com.green.winey_final.mypage.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelUserVo {
    private Long userId;
    private String email;
    private String nm;
    private String tel;
    private Long regionNm;
    private Long delYn;
}
