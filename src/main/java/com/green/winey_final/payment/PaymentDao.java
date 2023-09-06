package com.green.winey_final.payment;

import com.green.winey_final.common.entity.QCartEntity;
import com.green.winey_final.common.entity.QProductEntity;
import com.green.winey_final.common.entity.QSaleEntity;
import com.green.winey_final.payment.model.CartProductVo;
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

    public List<CartProductVo> selCartProduct(long userId) {
        JPQLQuery<CartProductVo> query = jpaQueryFactory.select(Projections.bean(CartProductVo.class
                        , p.productId, p.quantity
                        , new CaseBuilder().when(s.productEntity.isNotNull()).then(s.salePrice).otherwise(p.price).as("price")
                ))
                .from(c)
                .join(c.productEntity, p)
                .leftJoin(s).on(c.productEntity.productId.eq(s.productEntity.productId).and(s.saleYn.eq(1)))
                .where(c.userEntity.userId.eq(userId));
        return query.fetch();
    }
}
