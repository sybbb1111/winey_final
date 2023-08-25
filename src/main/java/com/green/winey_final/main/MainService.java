package com.green.winey_final.main;


import com.green.winey_final.common.entity.AromaEntity;
import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.main.model.WineSelRes;
import com.green.winey_final.main.model.WineTotalVo;
import com.green.winey_final.repository.MainRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
    private final MainMapper MAPPER;
    private final MainRepository PRODUCT_REP;

    public List<WineTotalVo> getWineCate() {
        List<ProductEntity> list = PRODUCT_REP.findAll();

//        list.stream().map(val -> {
//                List<AromaEntity> aromas = val.getAromaEntityList();
//                List<Long> aromaIds = aromas.stream()
//                        .map(aromaEntity -> aromaEntity.getAromaCategoryEntity().getAromaCategoryId())
//                        .toList();


        return list.stream().map(item ->
                WineTotalVo.builder()
                        .categoryId(item.getCategoryEntity().getCategoryId())
                        .featureId(item.getFeatureEntity().getFeatureId())
                        .countryId(item.getCountryEntity().getCountryId())
                        .productId(item.getProductId())
                        .nmKor(item.getNmKor())
                        .nmEng(item.getNmEng())
                        .price(item.getPrice())
                        .pic(item.getPic())
                        .promotion(item.getPromotion())
                        .beginner(item.getBeginner())
                        .alcohol(item.getAlcohol())
                        .quantity(item.getQuantity())
                        .build()
        ).toList();
    }

    public List<ProductEntity> getProductsByCategoryId(Long categoryId) {
        return PRODUCT_REP.findByCategoryEntityCategoryId(categoryId);
    }


}
