package com.green.winey_final.auth.model;

import com.green.winey_final.common.config.security.model.ProviderType;
import com.green.winey_final.common.config.security.model.RoleType;
import lombok.Data;

@Data
public class SignUpReqDto {

    private String email;
    private String upw;
    private RoleType roleType;
    private ProviderType providerType;

    private String unm;
    private String tel;
    private Long tos_yn;
    private Long regionNmId;
    private Long del_yn;
}
