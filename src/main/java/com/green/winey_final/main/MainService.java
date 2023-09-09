package com.green.winey_final.main;


import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.*;
import com.green.winey_final.main.model.WineCategoryDto;
import com.green.winey_final.main.model.WineCategoryDetailRes;
import com.green.winey_final.main.model.WineRes;
import com.green.winey_final.repository.ProductRepository;
import com.green.winey_final.repository.SaleRepository;
import com.green.winey_final.repository.SmallCategoryRepository;
import com.green.winey_final.repository.WinePairingRepository;
import com.green.winey_final.search.model.WineVo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static com.green.winey_final.common.entity.QProductEntity.productEntity;
import static com.green.winey_final.common.entity.QSaleEntity.saleEntity;
import static com.green.winey_final.common.entity.QSmallCategoryEntity.smallCategoryEntity;
import static com.green.winey_final.common.entity.QWinePairingEntity.winePairingEntity;
import static com.green.winey_final.common.entity.QUserEntity.userEntity;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService {
    private final AuthenticationFacade FACADE;
    private final JPAQueryFactory queryFactory;


    /** 할인 와인 리스트 */

    public WineRes wineList(Pageable pageable) {

        List<WineVo> list = saleWineList(pageable);
        Long count = count();

        return WineRes.builder()
                .count(count)
                .list(list)
                .build();
    }

    public Long count() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int startMonth = now.getMonthValue();
        LocalDate startOfMonth = LocalDate.of(year, startMonth, 1); // 이번 달의 시작일

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(saleEntity.startSale.goe(String.valueOf(startOfMonth)))
                .and(saleEntity.saleYn.eq(1));

        JPAQuery<Long> query = queryFactory.select(productEntity.productId.count())
                .from(productEntity)
                .innerJoin(saleEntity)
                .on(productEntity.productId.eq(saleEntity.productEntity.productId))
                .where(builder)
                .groupBy(productEntity.productId);

        return query.stream().count();
    }


    public List<WineVo> saleWineList(Pageable pageable) {

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int startMonth = now.getMonthValue();

        LocalDate startOfMonth = LocalDate.of(year, startMonth, 1); // 이번 달의 시작일
        LocalDate endOfMonth = startOfMonth.plusMonths(1).minusDays(1); // 이번 달의 마지막 날짜

        BooleanBuilder builder = new BooleanBuilder();

        builder.and(saleEntity.startSale.goe(String.valueOf(startOfMonth)))
//                .and(saleEntity.endSale.loe(String.valueOf(endOfMonth)))
//                .and(saleEntity.startSale.between(String.valueOf(saleEntity.endSale), endOfMonth.toString()))
                .and(saleEntity.saleYn.eq(1));

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
                .innerJoin(saleEntity)
                .on(productEntity.productId.eq(saleEntity.productEntity.productId))
                .where(builder)
                .groupBy(productEntity.productId)
                .orderBy(getAllOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return query.fetch();
    }

    private OrderSpecifier[] getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier> orders = new LinkedList();
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty().toLowerCase()) {//사용자가 혹시 대문자로 입력했을 경우에 소문자로 변경해줌
                    case "productid":
                        orders.add(new OrderSpecifier(direction, productEntity.productId));
                        break;
                    case "price":
                        orders.add(new OrderSpecifier(direction, productEntity.price));
                        break;
                }
            }
        }
        return orders.stream().toArray(OrderSpecifier[]::new);
    }


    /**
     * 카테고리별 와인 리스트 (카테고리별, 국가별, 나라별, 금액대별, 최신등록순 어쩌고 저쩌고)
     */
    public WineCategoryDetailRes categoryWine(WineCategoryDto dto) {

        List<WineVo> wineList = getWineLists(dto);

        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        Long count = countWineList(dto);
        int maxPage = (int) Math.ceil((double) count / dto.getRow());
        int isMore = maxPage > dto.getPage() ? 1 : 0;

        return WineCategoryDetailRes.builder()
                .categoryId(dto.getCategoryId())
                .bigCategoryId(dto.getBigCategoryId())
                .countryId(dto.getCountryId())
                .sort(dto.getSort())
                .price(dto.getPrice())
                .page(dto.getPage())
                .row(dto.getRow())
                .isMore(isMore)
                .maxPage(maxPage)
                .count((int) count.longValue())
                .wineList(wineList)
                .build();
    }

    public List<WineVo> getWineLists(WineCategoryDto dto) {
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
            builder.and(productEntity.price.gt(0));
        } else if (dto.getPrice() == 1) {
            builder.and(productEntity.price.lt(20000));
        } else if (dto.getPrice() == 2) {
            builder.and(productEntity.price.between(20000, 50000));
        } else if (dto.getPrice() == 3) {
            builder.and(productEntity.price.between(50000, 100000));
        } else {
            builder.and(productEntity.price.goe(100000));
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
            query.orderBy(productEntity.price.desc());
        } else if (dto.getSort() == 3) {
            query.orderBy(productEntity.price.asc());
        }
        int result = (dto.getPage() - 1) * dto.getRow();

        return query.offset(result)
                .limit(dto.getRow())
                .fetch();
    }

    public Long countWineList(WineCategoryDto dto) {
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
            builder.and(productEntity.price.gt(0));
        } else if (dto.getPrice() == 1) {
            builder.and(productEntity.price.lt(20000));
        } else if (dto.getPrice() == 2) {
            builder.and(productEntity.price.between(20000, 50000));
        } else if (dto.getPrice() == 3) {
            builder.and(productEntity.price.between(50000, 100000));
        } else {
            builder.and(productEntity.price.goe(100000));
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

    /** 카테고리별 와인 리스트 여기까지 한 묶음 */


}
