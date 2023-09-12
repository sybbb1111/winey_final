package com.green.winey_final.detail;

import com.green.winey_final.common.entity.*;
import com.green.winey_final.detail.model.*;
import com.green.winey_final.repository.ProductKorRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DetailService {

    private final ProductKorRepository productKorRepository;
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final QProductEntity product = QProductEntity.productEntity;
    private final QCategoryEntity category = QCategoryEntity.categoryEntity;
    private final QCountryEntity country = QCountryEntity.countryEntity;
    private final QFeatureEntity feature = QFeatureEntity.featureEntity;
    private final QWinePairingEntity winePairing = QWinePairingEntity.winePairingEntity;
    private final QSmallCategoryEntity smallCategory = QSmallCategoryEntity.smallCategoryEntity;
    private final QReviewEntity review =QReviewEntity.reviewEntity;
    private final QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;
    private final QAromaEntity aroma =QAromaEntity.aromaEntity;
    private final QAromaCategoryEntity aromaCategory =QAromaCategoryEntity.aromaCategoryEntity;
    private final QSaleEntity sale = QSaleEntity.saleEntity;

    public WineVo4 selWineDetail(Long productId) {
        WineDetailVo vo = jpaQueryFactory.select(Projections.constructor(WineDetailVo.class,
                        product.productId, product.nmKor, product.nmEng,category.categoryId.as("temp"), product.price, product.pic,
                        product.promotion, product.beginner, product.alcohol, product.quantity, category.nm.as("categoryNm"),
                        country.nm.as("countryNm"),feature.sweety,feature.acidity,feature.body))
                .from(product)
                .leftJoin(category).on(product.categoryEntity.categoryId.eq(category.categoryId))
                .leftJoin(country).on(product.countryEntity.countryId.eq(country.countryId))
                .leftJoin(feature).on(product.featureEntity.featureId.eq(feature.featureId))
                .where(product.productId.eq(productId))
                .fetchOne();

        List<String> selPairing = jpaQueryFactory.select(Projections.constructor(String.class,smallCategory.sKind.as("selReview")))
                .from(product)
                .leftJoin(winePairing).on(product.productId.eq(winePairing.productEntity.productId))
                .leftJoin(smallCategory).on(winePairing.smallCategoryEntity.smallCategoryId.eq(smallCategory.smallCategoryId))
                .where(product.productId.eq(productId))
                .orderBy(smallCategory.smallCategoryId.desc())
                .fetch();

        if (vo.getTemp()==1L) {
            vo.setTemp(10L);
        } else if (vo.getTemp()==2L) {
            vo.setTemp(15L);
        } else {
            vo.setTemp(18L);
        }

        List<String> selCount = new ArrayList();

        for (int i = 1; i <= 3; i++) {
            SelCountVo selCountVo = new SelCountVo();
            selCountVo.setReviewLevel(i);
            selCountVo.setProductId(productId);

           List<Long> countQuery = jpaQueryFactory.select(Projections.constructor(Long.class, review.reviewLevel.count()))
                    .from(review)
                    .leftJoin(orderDetail).on(orderDetail.orderDetailId.eq(review.orderDetailEntity.orderDetailId))
                    .where(orderDetail.productEntity.productId.eq(productId).and(review.reviewLevel.eq(selCountVo.getReviewLevel())))
                    .fetch(); // 쿼리 실행 및 결과 가져오기
            for (Long count : countQuery) {
                selCount.add(count.toString());
            }}
        List<String> selAroma = jpaQueryFactory.select(Projections.constructor(String.class, aromaCategory.nm))
                .from(product)
                .leftJoin(aroma).on(aroma.productEntity.productId.eq(product.productId))
                .leftJoin(aromaCategory).on(aromaCategory.aromaCategoryId.eq(aroma.aromaCategoryEntity.aromaCategoryId))
                .where(product.productId.eq(productId))
                .orderBy(aromaCategory.aromaCategoryId.desc())
                .fetch();

        long level = 0;
        long sum = vo.getSweety() + vo.getAcidity() + vo.getBody();
        if (sum < 8) {
            level = 1L;
        } else if (sum >= 8 && sum < 11) {
            level = 2L;
        } else if (sum >= 11 && sum < 16) {
            level = 3L;
        }

        SelSale selSale = jpaQueryFactory.select(Projections.constructor(SelSale.class,sale.productEntity.productId,sale.sale, sale.salePrice))
                .from(product)
                .leftJoin(sale).on(sale.productEntity.productId.eq(product.productId))
                .where(product.productId.eq(productId).and(sale.saleYn.eq(1))).fetchOne();

        if (selSale != null) {
            selSale.setProductId(productId);
        } else if (selSale == null) {
            selSale = null;
            log.info("할인상품아님");
        }
        return WineVo4.builder()
                .wineDetailVo(vo)
                .selPairing(selPairing)
                .selReview(selCount)
                .selAroma(selAroma)
                .Level(level)
                .selSale(selSale)
                .build();
    }


    public SelWineKorNm selKorNm(Long productId) {
        Optional<ProductEntity> optEntity = productKorRepository.findById(productId);
        ProductEntity entity = optEntity.get();

        return SelWineKorNm.builder()
                .productId(entity.getProductId())
                .nmKor(entity.getNmKor())
                .build();
    }


}
