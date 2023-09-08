package com.green.winey_final.order;


import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.*;
import com.green.winey_final.common.entity.QOrderDetailEntity;
import com.green.winey_final.common.entity.QOrderEntity;
import com.green.winey_final.common.entity.QProductEntity;
import com.green.winey_final.common.entity.QReviewEntity;
import com.green.winey_final.common.entity.QSaleEntity;
import com.green.winey_final.common.entity.QStoreEntity;
import com.green.winey_final.common.entity.QUserEntity;
import com.green.winey_final.order.model.DetailVo;
import com.green.winey_final.order.model.OrderDetailVo1;
import com.green.winey_final.order.model.OrderDetailVo2;
import com.green.winey_final.order.model.SelOrderVo;
import com.green.winey_final.repository.OrderRepository;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;




@Slf4j
@Service
@RequiredArgsConstructor
@PersistenceContext
public class OrderService {

    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    private final OrderRepository orderRepository;
    private final AuthenticationFacade facade;

    List<SelOrderVo> selOrder() {
        QUserEntity user = QUserEntity.userEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;
        QProductEntity product = QProductEntity.productEntity;
        QStoreEntity store = QStoreEntity.storeEntity;
        Long userPk = facade.getLoginUserPk();

        Predicate predicate = user.userId.eq(userPk);

        List<SelOrderVo> result = jpaQueryFactory
                .select(Projections.constructor(SelOrderVo.class,
                        Expressions.stringTemplate("DATE_FORMAT({0},{1})",order.orderDate, ConstantImpl.create("%Y-%m-%d")), order.orderId, order.userEntity.userId,
                        product.nmKor, order.payment, order.totalOrderPrice,
                        store.nm, order.pickupTime, order.orderStatus, order.count()))
                .from(order)
                .leftJoin(orderDetail).on(orderDetail.orderEntity.eq(order))  // 수정된 부분
                .leftJoin(product).on(orderDetail.productEntity.productId.eq(product.productId))
                .leftJoin(store).on(order.storeEntity.storeId.eq(store.storeId))
                .where(predicate)
                .groupBy(order.orderId)
                .orderBy(order.orderDate.desc(), order.orderId.desc())
                .fetch();

        for (SelOrderVo entity : result) {

            UserEntity entity1 = new UserEntity();
            entity1.setUserId(userPk);

            entity.setOrderDate(entity.getOrderDate());
            entity.setUserId(userPk);
            entity.setOrderId(entity.getOrderId());
            entity.setPayment(entity.getPayment());
            entity.setTotalOrderPrice(entity.getTotalOrderPrice());
            entity.setNm("이마트" + entity.getNm());
            entity.getPickupTime();
            entity.setOrderStatus(entity.getOrderStatus());
            if (entity.getCount() >= 2) {
                entity.setNmKor(entity.getNmKor() + " 외 " + (entity.getCount() - 1) + "건");
            } else if (entity.getCount() == 1) {
                entity.setNmKor(entity.getNmKor());
            }
        }




        return result;
    }


    public int cancelOrder(Long orderId) {
        Optional<OrderEntity> optentity = orderRepository.findById(orderId);
        OrderEntity entity = optentity.get();
        entity.setOrderStatus(6);
        orderRepository.save(entity);
        return 1;
    }


    int pickupFinishOrder(Long orderId) {
        Optional<OrderEntity> optentity = orderRepository.findById(orderId);
        OrderEntity entity = optentity.get();
        entity.setOrderStatus(5);
        orderRepository.save(entity);
        return 1;
    }


    public DetailVo selOrderDetail(Long orderId) {

        Long userPk = facade.getLoginUserPk();
        QUserEntity user = QUserEntity.userEntity;
        QOrderEntity order = QOrderEntity.orderEntity;
        QOrderDetailEntity orderDetail = QOrderDetailEntity.orderDetailEntity;
        QProductEntity product = QProductEntity.productEntity;
        QSaleEntity sale = QSaleEntity.saleEntity;
        QReviewEntity review = QReviewEntity.reviewEntity;
        QStoreEntity store = QStoreEntity.storeEntity;

        Predicate predicate = order.orderId.eq(orderId);
        List<OrderDetailVo1> vo1 = jpaQueryFactory
                .select(Projections.constructor(OrderDetailVo1.class,
                        orderDetail.orderDetailId, product.nmKor, product.nmEng,
                        orderDetail.salePrice, orderDetail.quantity, product.pic,
                        product.price, product.productId,
                        review.reviewId.count().as("reviewYn")))
                .from(orderDetail)
                .innerJoin(product).on(orderDetail.productEntity.productId.eq(product.productId))
                .innerJoin(order).on(orderDetail.orderEntity.orderId.eq(order.orderId))
                .innerJoin(sale).on(product.productId.eq(sale.productEntity.productId))
                .leftJoin(review).on(orderDetail.orderDetailId.eq(review.orderDetailEntity.orderDetailId))
                .where(order.orderId.eq(orderId).and(order.userEntity.userId.eq(userPk)))
                .groupBy(orderDetail.orderDetailId)
                .fetch();

        for (OrderDetailVo1 detailVo1 : vo1) {
            if (detailVo1.getReviewYn() >= 1L) {
                detailVo1.setReviewYn(1L);
            }
        }

        OrderDetailVo2 vo2 = jpaQueryFactory.select(Projections.constructor(OrderDetailVo2.class,
                        order.orderDate, order.payment, order.pickupTime,
                        order.orderStatus, order.totalOrderPrice, store.nm))
                .from(order)
                .leftJoin(store).on(order.storeEntity.storeId.eq(store.storeId))
                .where(order.orderId.eq(orderId).and(order.userEntity.userId.eq(userPk)))
                .fetchOne();


        vo2.setStoreNm("이마트 " + vo2.getStoreNm());


        return DetailVo.builder()
                .vo1(vo1)
                .vo2(vo2)
                .build();
    }


}
