package com.green.winey_final.auth.model;

import com.green.winey_final.common.config.security.model.RoleType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class AuthResLoginVo {
    private AuthResVo authResVo;

    //private String accessToken;


    private RoleType roleType;

}
