package com.green.winey_final.search;

import com.green.winey_final.search.model.WineSearchDto;
import com.green.winey_final.search.model.WineSelDetailRes;
import com.green.winey_final.search.model.WineVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.green.winey_final.common.entity.QProductEntity.productEntity;
import static com.green.winey_final.common.entity.QSaleEntity.saleEntity;
import static com.green.winey_final.common.entity.QSmallCategoryEntity.smallCategoryEntity;
import static com.green.winey_final.common.entity.QWinePairingEntity.winePairingEntity;


@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final JPAQueryFactory queryFactory;

    public WineSelDetailRes searchWine1(WineSearchDto dto) {

        List<WineVo> wineList = getWineList(dto);

        Long count = countWineList(dto);
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

    public List<WineVo> getWineList(WineSearchDto dto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (dto.getCategoryId() != null) {
            builder.and(productEntity.categoryEntity.categoryId.eq(dto.getCategoryId()));
        }

        if (dto.getBigCategoryId() != null) {
            builder.and(smallCategoryEntity.bigCategoryEntity.bigCategoryId.eq(dto.getBigCategoryId()));
        }

        if (dto.getCountryId() != null) {
            builder.and(productEntity.countryEntity.countryId.eq(dto.getCountryId()));
        }

            if (dto.getPrice() == 0) {
                builder.and(saleEntity.salePrice.gt(0));
            } else if (dto.getPrice() == 1) {
                builder.and(saleEntity.salePrice.lt(20000));
            } else if (dto.getPrice() == 2) {
                builder.and(saleEntity.salePrice.between(20000, 50000));
            } else if (dto.getPrice() == 3) {
                builder.and(saleEntity.salePrice.between(50000, 100000));
            } else if (dto.getPrice() == 4){
                builder.and(saleEntity.salePrice.goe(100000));
            }

        if (dto.getText() != null && !dto.getText().isEmpty()) {
            builder.and(productEntity.nmKor.contains(dto.getText()).or(productEntity.nmEng.contains(dto.getText())));
        }
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
                .where(builder)
                .groupBy(productEntity.productId);

        if (dto.getSort() == 0) {
            query.orderBy(productEntity.productId.asc());
        } else if (dto.getSort() == 1) {
            query.orderBy(productEntity.productId.desc());
        } else if (dto.getSort() == 2) {
            query.orderBy(saleEntity.salePrice.desc());
        } else if (dto.getSort() == 3) {
            query.orderBy(saleEntity.salePrice.asc());
        }
        int result = (dto.getPage() - 1) * dto.getRow();

        return query.offset(result)
                .limit(dto.getRow())
                .fetch();
    }

    public Long countWineList(WineSearchDto dto) {
        BooleanBuilder builder = new BooleanBuilder();

        if (dto.getCategoryId() != null) {
            builder.and(productEntity.categoryEntity.categoryId.eq(dto.getCategoryId()));
        }

        if (dto.getBigCategoryId() != null) {
            builder.and(smallCategoryEntity.bigCategoryEntity.bigCategoryId.eq(dto.getBigCategoryId()));
        }

        if (dto.getCountryId() != null) {
            builder.and(productEntity.countryEntity.countryId.eq(dto.getCountryId()));
        }

        if (dto.getPrice() == 0) {
            builder.and(saleEntity.salePrice.gt(0));
        } else if (dto.getPrice() == 1) {
            builder.and(saleEntity.salePrice.lt(20000));
        } else if (dto.getPrice() == 2) {
            builder.and(saleEntity.salePrice.between(20000, 50000));
        } else if (dto.getPrice() == 3) {
            builder.and(saleEntity.salePrice.between(50000, 100000));
        } else {
            builder.and(saleEntity.salePrice.goe(100000));
        }

        if (dto.getText() != null && !dto.getText().isEmpty()) {
            builder.and(productEntity.nmKor.contains(dto.getText()).or(productEntity.nmEng.contains(dto.getText())));
        }

        return queryFactory
                .select(productEntity.productId.countDistinct())
                .from(productEntity)
                .innerJoin(saleEntity).on(productEntity.productId.eq(saleEntity.productEntity.productId))
                .innerJoin(winePairingEntity).on(winePairingEntity.productEntity.productId.eq(productEntity.productId))
                .innerJoin(smallCategoryEntity).on(winePairingEntity.smallCategoryEntity.smallCategoryId.eq(smallCategoryEntity.smallCategoryId))
                .where(builder)
                .fetchOne();
    }
}
