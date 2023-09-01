package com.green.winey_final.main;


import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.config.security.model.RoleType;
import com.green.winey_final.common.entity.*;
import com.green.winey_final.main.model.WineCategoryDto;
import com.green.winey_final.main.model.WineCategoryDetailRes;
import com.green.winey_final.repository.ProductRepository;
import com.green.winey_final.repository.SmallCategoryRepository;
import com.green.winey_final.repository.WinePairingRepository;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    private final ProductRepository productRep;
    private final SmallCategoryRepository smallCategoryRep;
    private final WinePairingRepository winePairingRep;
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;



    public List<ProductEntity> getCountry(Long countryId) {
        //페이징 처리
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }};
        pageable = PageRequest.of(pageable.getPageNumber(), 6);

        return productRep.findByCountryEntityCountryId(countryId, pageable);
    }

    /** 매일 6개 랜덤 와인 추천 */


    private List<WineVo> getRandomWine(WineCategoryDto dto) {

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
                .where(userEntity.userId.eq(FACADE.getLoginUser().getUserId()))
                .groupBy(productEntity.productId);


        return query.offset(dto.getStartIdx())
                .limit(dto.getRow())
                .fetch();
    }








    /** 카테고리별 와인 리스트 (카테고리별, 국가별, 나라별, 금액대별, 최신등록순 어쩌고 저쩌고) */
    public WineCategoryDetailRes categoryWine(WineCategoryDto dto) {

        List<WineVo> wineList = getWineList(dto);

        dto.setStartIdx((dto.getPage() - 1));

        Long count = countLastWine();
        int maxPage = (int) Math.ceil((double) count / dto.getRow());
        int isMore = maxPage > dto.getPage() ? 1 : 0;

//        Pageable pageable = PageRequest.of(dto.getPage() - 1, 9);

        WineCategoryDetailRes response = WineCategoryDetailRes.builder()
                .categoryId(dto.getCategoryId())
                .bigCategoryId(dto.getBigCategoryId())
                .countryId(dto.getCountryId())
//                .text(dto.getText())
                .sort(dto.getSort())
                .price(dto.getPrice())
                .page(dto.getPage())
                .row(dto.getRow())
                .isMore(isMore)
                .maxPage(maxPage)
                .count(wineList.size())
                .wineList(wineList)
                .build();

        return response;
    }

    private List<WineVo> getWineList(WineCategoryDto dto) {
        Predicate predicate = productEntity.categoryEntity.categoryId.eq(dto.getCategoryId())
                .and(smallCategoryEntity.bigCategoryEntity.bigCategoryId.eq(dto.getBigCategoryId()))
                .and(productEntity.countryEntity.countryId.eq(dto.getCountryId()))
                .and(dto.getPrice() == 0 ? productEntity.price.gt(0)
                        : dto.getPrice() == 1 ? productEntity.price.lt(20000)
                        : dto.getPrice() == 2 ? productEntity.price.between(20000, 50000)
                        : dto.getPrice() == 3 ? productEntity.price.between(50000, 100000)
                        : productEntity.price.goe(100000));

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

    public Long countLastWine() {
        QProductEntity productEntity = QProductEntity.productEntity;

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        BooleanExpression predicate = null;

//        BooleanExpression predicate = productEntity.productId.eq(productId).isNull();

        return queryFactory
                .select(productEntity.productId.count())
                .from(productEntity)
                .where(predicate)
                .fetchOne();
    }

    /** 카테고리별 와인 리스트 여기까지 한 묶음 */

    /** 연습용 */
    public List<ProductEntity> getProductsByCategoryId(Long categoryId) {
        //페이징 처리
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }};
        pageable = PageRequest.of(pageable.getPageNumber(), 9);

        return productRep.findByCategoryEntityCategoryId(categoryId, pageable);
    }

    /** 음식별 와인..JPA실패 */
    public List<SmallCategoryEntity> getFood(Long bigCate) {
        //페이징 처리

//        Pageable pageable = PageRequest.of(page - 1, 6);

        return smallCategoryRep.findTop2ByBigCategoryEntity_BigCategoryId(bigCate);
    }

//    public List<WinePairingEntity> getPairing(Long productId) {
//        //페이징 처리
//
////        Pageable pageable = PageRequest.of(page - 1, 6);
//
//        return winePairingRep.findWinePairingEntityByProductEntity_ProductIdDistinct(productId);
//    }

}
