package com.green.winey_final.auth.model;

import lombok.Data;

@Data
public class SignInReqDto {
    private String uid;
    private String upw;
}