package com.green.winey_final.payment;

import com.green.winey_final.common.entity.*;
import com.green.winey_final.payment.model.CartProductVo;
import com.green.winey_final.payment.model.RegionSelVo;
import com.green.winey_final.payment.model.SaleProductVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentDao {
    private final JPAQueryFactory jpaQueryFactory;

    private final QCartEntity c = QCartEntity.cartEntity;
    private final QProductEntity p = QProductEntity.productEntity;
    private final QSaleEntity s = QSaleEntity.saleEntity;
    private final QUserEntity u = QUserEntity.userEntity;
    private final QRegionNmEntity r = QRegionNmEntity.regionNmEntity;
    private final QStoreEntity st = QStoreEntity.storeEntity;

    public List<CartProductVo> selCartProduct(long userId) {
        JPQLQuery<CartProductVo> query = jpaQueryFactory.select(Projections.bean(CartProductVo.class
                        , p.productId, c.quantity
                        , new CaseBuilder().when(s.productEntity.isNotNull()).then(s.salePrice).otherwise(p.price).as("price")
                ))
                .from(c)
                .join(c.productEntity, p)
                .leftJoin(s).on(c.productEntity.productId.eq(s.productEntity.productId).and(s.saleYn.eq(1)))
                .where(c.userEntity.userId.eq(userId));
        return query.fetch();
    }

    public SaleProductVo selProduct(long productId) {
        return jpaQueryFactory.select(Projections.bean(SaleProductVo.class, new CaseBuilder().when(s.productEntity.isNotNull()).then(s.salePrice).otherwise(p.price).as("price")))
                .from(p)
                .leftJoin(s).on(s.productEntity.productId.eq(p.productId).and(s.saleYn.eq(1)))
                .where(p.productId.eq(productId))
                .fetchOne();
    }

    public List<RegionSelVo> selRegion(long userId) {
        return jpaQueryFactory.select(Projections.bean(RegionSelVo.class, r.regionNmId, r.regionNm, st.storeId, st.nm, st.address))
                .from(st)
                .join(st.regionNmEntity, r)
                .join(u).on(u.regionNmEntity.regionNmId.eq(r.regionNmId))
                .where(u.userId.eq(userId)).fetch();
    }
}
