package com.green.winey_final.main;


import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.main.model.WineSelRes;
import com.green.winey_final.main.model.WineTotalVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
    private final MainMapper MAPPER;
    private final MainRepository PRODUCT_REP;

    public List<WineTotalVo> selRedWines(Pageable pageable) {
        List<ProductEntity> vo = PRODUCT_REP.findAllByRedWine(1L);
        WineSelRes dto = WineSelRes.builder()
                .startIdx((pageable.getPageNumber() - 1) * pageable.getPageSize())
                .list(vo)
                .build();
        return MAPPER.selRedWines(dto);
    }
}
