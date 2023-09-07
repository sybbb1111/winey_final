package com.green.winey_final.admin.model;

import com.querydsl.core.annotations.QueryProjection;
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

    @QueryProjection
    public UserVo(Long userId, String email, String nm, int regionNmId, String createdAt) {
        this.userId = userId;
        this.email = email;
        this.nm = nm;
        this.regionNmId = regionNmId;
        this.createdAt = createdAt;
    }
}
