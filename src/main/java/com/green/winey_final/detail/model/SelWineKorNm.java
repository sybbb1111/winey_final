package com.green.winey_final.detail.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SelWineKorNm {
    private Long productId;
    private String nmKor;
}
