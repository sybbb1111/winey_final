package com.green.winey_final.detail2.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SelReview2 {
    private Long productId;
    private int reviewCount;
}
