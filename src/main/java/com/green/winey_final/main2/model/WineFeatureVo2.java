package com.green.winey_final.main2.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WineFeatureVo2 {
    private Long productId;
    private Long featureId;
    private int sweety;
    private int acidity;
    private int body;
}
