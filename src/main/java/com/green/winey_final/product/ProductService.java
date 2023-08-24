package com.green.winey_final.product;


import com.green.winey_final.product.model.WineSelDto;
import com.green.winey_final.product.model.WineTotalVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductMapper MAPPER;
    private final ProductRepository PRODUCT_REP;

    public List<WineTotalVo> selWine(Pageable pageable) {
        WineSelDto dto = WineSelDto.builder()
                .startIdx((pageable.getPageNumber() - 1) * pageable.getPageSize())
                .build();
        return MAPPER.selWine(dto);
    }
}
