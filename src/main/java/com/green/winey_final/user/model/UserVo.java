package com.green.winey_final.user.model;

import com.green.winey_final.common.config.security.model.ProviderType;
import com.green.winey_final.common.config.security.model.RoleType;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserVo {
    private Long userId;

    private Long del_yn;
    private String email;
    private ProviderType providerType;
    private RoleType roleType;

    private String tel;
    private Long tos_yn;
    private String unm;
    private String upw;
    private Long regionNmId;
}
