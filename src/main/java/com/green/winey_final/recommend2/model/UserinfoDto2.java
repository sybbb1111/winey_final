package com.green.winey_final.recommend2.model;

import lombok.Data;

import java.util.List;

@Data
public class UserinfoDto2 {
    private Long userId;
    private List<Long> productId;
}

