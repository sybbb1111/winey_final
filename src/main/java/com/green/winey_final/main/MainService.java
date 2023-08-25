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
    private final MainRepository MAIN_REP;

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

//    public ItemSelDetailRes searchItem(ItemSearchDto dto) {
//        // 아이템 리스트 뿌려주기 전에 로그인 상태 체크
//
//        if(!FACADE.isLogin()) {
//            dto.setIuser(0L);
//        } else {
//            dto.setIuser(FACADE.getLoginUserPk());
//            log.info("iuser: {}",dto.getIuser());
//        }
//        dto.setStartIdx((dto.getPage()-1) * dto.getRow());
//        List<ItemVo> list = MAPPER.searchItem(dto);
//        int count = MAPPER.selLastItem(dto);
//        int maxPage = (int)Math.ceil((double) count /dto.getRow());
//        int isMore = maxPage > dto.getPage() ? 1 : 0;
//
//        return ItemSelDetailRes.builder()
//                .iitemCategory(dto.getIitemCategory())
//                .text(dto.getText())
//                .sort(dto.getSort())
//                .maxPage(maxPage)
//                .startIdx(dto.getStartIdx())
//                .isMore(isMore)
//                .page(dto.getPage())
//                .row(dto.getRow())
//                .itemList(list)
//                .build();
//    }

}
