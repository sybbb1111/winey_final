package com.green.winey_final.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResVo {
    private String accessToken;
}
