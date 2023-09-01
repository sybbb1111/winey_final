package com.green.winey_final.cart;

import com.green.winey_final.cart.model.CartSelDto;
import com.green.winey_final.cart.model.CartVo;
import com.green.winey_final.common.entity.QCartEntity;
import com.green.winey_final.common.entity.QProductEntity;
import com.green.winey_final.common.entity.QSaleEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CartDao {
    private final JPAQueryFactory jpaQueryFactory;

    private final QCartEntity cart = QCartEntity.cartEntity;
    private final QProductEntity product = QProductEntity.productEntity;
    private final QSaleEntity sale = QSaleEntity.saleEntity;

    public List<CartVo> selCartAll(CartSelDto dto) {
        NumberExpression<Integer> colPrice = new CaseBuilder().when(sale.saleYn.eq(1).and(sale.salePrice.goe(0))).then(sale.salePrice).otherwise(product.price).as("price");

        return jpaQueryFactory.select(Projections.bean(CartVo.class, cart.cartId, cart.quantity
                , product.productId, product.nmKor, product.nmEng, product.pic, colPrice))
                .from(cart)
                .join(cart.productEntity, product)
                .leftJoin(sale).on(product.eq(sale.productEntity))
                .where(cart.userEntity.userId.eq(dto.getUserId()))
                .fetch();
    }
}
