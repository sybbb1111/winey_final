package com.green.winey_final.payment;

import com.green.winey_final.common.entity.QCartEntity;
import com.green.winey_final.common.entity.QProductEntity;
import com.green.winey_final.payment.model.CartProductVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentDao {
    private final JPAQueryFactory jpaQueryFactory;
    private final QCartEntity c = QCartEntity.cartEntity;
    private final QProductEntity p = QProductEntity.productEntity;

    public List<CartProductVo> selCartProduct(long userId) {
        JPQLQuery<CartProductVo> query = jpaQueryFactory.select(Projections.bean(CartProductVo.class, p.productId, p.quantity, p.price, p.promotion))
                .from(c)
                .join(c.productEntity, p)
                .where(c.userEntity.userId.eq(userId));
        return query.fetch();
    }
}
