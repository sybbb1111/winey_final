package com.green.winey_final.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StoreList {
    private PageDto page;
    private List<StoreVo> list;
}
