package com.green.winey_final.main;


import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.AromaEntity;
import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.main.model.*;
import com.green.winey_final.repository.MainRepository;
import com.twitter.penguin.korean.TwitterKoreanProcessorJava;
import com.twitter.penguin.korean.phrase_extractor.KoreanPhraseExtractor;
import com.twitter.penguin.korean.tokenizer.KoreanTokenizer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import scala.collection.Seq;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
    private final MainMapper MAPPER;
    private final MainRepository MAIN_REP;
    private final AuthenticationFacade FACADE;

    public List<WineTotalVo> getWineCate() {
        List<ProductEntity> list = MAIN_REP.findAll();

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
//
    public List<ProductEntity> getProductsByCategoryId(Long categoryId) {
        return MAIN_REP.findByCategoryEntityCategoryId(categoryId);
    }

    public WineSelDetailRes searchWine(WineSearchDto dto) {

        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineVo> list = MAPPER.searchWine(dto);
        int count = MAPPER.selLastWine(dto);
        int maxPage = (int) Math.ceil((double) count / dto.getRow());
        int isMore = maxPage > dto.getPage() ? 1 : 0;

        return WineSelDetailRes.builder()
                .categoryId(dto.getCategoryId())
                .bigCategoryId(dto.getBigCategoryId())
                .countryId(dto.getCountryId())
                .text(dto.getText())
                .sort(dto.getSort())
                .price(dto.getPrice())
                .startIdx(dto.getStartIdx())
                .page(dto.getPage())
                .row(dto.getRow())
                .isMore(isMore)
                .maxPage(maxPage)
                .wineList(list)
                .build();
    }



}
