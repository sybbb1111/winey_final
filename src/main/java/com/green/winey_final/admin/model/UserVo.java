package com.green.winey_final.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserVo {
    Long userId;
    String email;
    String nm;
    int regionNmId;
    String createdAt;
}
