package com.green.winey_final.detail.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SelPairing {
    private Long productId;
    private List<String> sKind;
}
