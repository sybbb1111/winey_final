package com.green.winey_final.auth.model;

import com.green.winey_final.common.config.security.model.ProviderType;
import com.green.winey_final.common.config.security.model.RoleType;
import lombok.Data;

@Data
public class SignUpReqDto {

    private String email;
    private String upw;

    private String unm;
    private String tel;
    private Long regionNmId;
}
