package com.green.winey_final.search;

import com.green.winey_final.common.entity.QProductEntity;
import com.green.winey_final.search.model.WineSearchDto;
import com.green.winey_final.search.model.WineSelDetailRes;
import com.green.winey_final.search.model.WineVo;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;




@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public WineSelDetailRes searchWine1(WineSearchDto dto) {

        List<WineVo> wineList = getWineList(dto);

        dto.setStartIdx((dto.getPage() - 1));

        Long count = countLastWine(dto.getText());
        int maxPage = (int) Math.ceil((double) count / dto.getRow());
        int isMore = maxPage > dto.getPage() ? 1 : 0;


        WineSelDetailRes response = WineSelDetailRes.builder()
                .categoryId(dto.getCategoryId())
                .bigCategoryId(dto.getBigCategoryId())
                .countryId(dto.getCountryId())
                .text(dto.getText())
                .sort(dto.getSort())
                .price(dto.getPrice())
                .page(dto.getPage())
                .row(dto.getRow())
                .isMore(isMore)
                .maxPage(maxPage)
                .count((int) count.longValue())
                .wineList(wineList)
                .build();

        return response;
    }

    private List<WineVo> getWineList(WineSearchDto dto) {
        Predicate predicate = productEntity.categoryEntity.categoryId.eq(dto.getCategoryId())
                .and(smallCategoryEntity.bigCategoryEntity.bigCategoryId.eq(dto.getBigCategoryId()))
                .and(productEntity.countryEntity.countryId.eq(dto.getCountryId()))
                .and(dto.getPrice() == 0 ? productEntity.price.gt(0)
                        : dto.getPrice() == 1 ? productEntity.price.lt(20000)
                        : dto.getPrice() == 2 ? productEntity.price.between(20000, 50000)
                        : dto.getPrice() == 3 ? productEntity.price.between(50000, 100000)
                        : productEntity.price.goe(100000))
                .and(productEntity.nmKor.likeIgnoreCase("%" + dto.getText() + "%"));

        JPAQuery<WineVo> query = queryFactory.select(
                        Projections.constructor(
                                WineVo.class,
                                productEntity.productId,
                                productEntity.nmKor,
                                productEntity.nmEng,
                                productEntity.price,
                                productEntity.pic,
                                productEntity.promotion,
                                productEntity.beginner,
                                saleEntity.sale,
                                saleEntity.salePrice,
                                saleEntity.saleYn))
                .from(productEntity)
                .innerJoin(saleEntity).on(productEntity.productId.eq(saleEntity.productEntity.productId))
                .innerJoin(winePairingEntity).on(winePairingEntity.productEntity.productId.eq(productEntity.productId))
                .innerJoin(smallCategoryEntity).on(winePairingEntity.smallCategoryEntity.smallCategoryId.eq(smallCategoryEntity.smallCategoryId))
                .where(predicate)
                .groupBy(productEntity.productId);

        if (dto.getSort() == 0) {
            query.orderBy(productEntity.productId.asc());
        } else if (dto.getSort() == 1) {
            query.orderBy(productEntity.productId.desc());
        } else if (dto.getSort() == 2) {
            query.orderBy(productEntity.price.desc());
        } else if (dto.getSort() == 3) {
            query.orderBy(productEntity.price.asc());
        }

        return query.offset(dto.getStartIdx())
                .limit(dto.getRow())
                .fetch();
    }

    public Long countLastWine(String text) {

        BooleanExpression predicate = null;

        if (text != null) {
            predicate = productEntity.nmKor.likeIgnoreCase("%" + text + "%");
        }

        return queryFactory
                .select(productEntity.productId.count())
                .from(productEntity)
                .where(predicate)
                .fetchOne();
    }
}
