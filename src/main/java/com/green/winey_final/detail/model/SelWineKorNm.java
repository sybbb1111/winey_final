package com.green.winey_final.detail.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SelWineKorNm {
    private Long productId;
    private String nmKor;
}
