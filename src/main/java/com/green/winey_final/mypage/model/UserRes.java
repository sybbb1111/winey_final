package com.green.winey_final.mypage.model;

import com.green.winey_final.common.config.security.model.ProviderType;
import com.green.winey_final.common.config.security.model.RoleType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserRes {
    private Long userId;
    private String email;
    private String unm;
    private String tel;

    private Long regionNmId;
    private String regionNm;

    private Long del_yn;

    private ProviderType providerType;
    private RoleType roleType;

}
