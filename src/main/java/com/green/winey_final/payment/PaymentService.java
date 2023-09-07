package com.green.winey_final.payment;

import com.green.winey_final.common.config.exception.PaymentErrorCode;
import com.green.winey_final.common.config.exception.RestApiException;
import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.*;
import com.green.winey_final.payment.model.*;
import com.green.winey_final.repository.CartRepository;
import com.green.winey_final.repository.OrderDetailRepository;
import com.green.winey_final.repository.OrderRepository;
import com.green.winey_final.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AuthenticationFacade facade;
    private final OrderRepository orderRep;
    private final OrderDetailRepository orderDetailRep;
    private final PaymentDao dao;
    private final CartRepository cartRep;
    private final ReviewRepository reviewRep;

    @Transactional
    public long insPayment(PaymentInsDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<CartProductVo> cartProductList = dao.selCartProduct(facade.getLoginUserPk());
        if(cartProductList.size() == 0) {
            throw new RestApiException(PaymentErrorCode.NO_PRODUCT_IN_CART);
        }

        Optional<Integer> optTotalPrice = cartProductList.stream().map(item -> item.getPrice() * item.getQuantity()).reduce((x, y) -> x + y);
        int totalPrice = optTotalPrice.isPresent() ? optTotalPrice.get() : 0;

        OrderEntity entity = OrderEntity.builder()
                .orderStatus(1)
                .orderDate(LocalDateTime.now())
                .payment(1)
                .pickupTime(LocalDateTime.parse(dto.getPickupTime(), formatter))
                .storeEntity(StoreEntity.builder().storeId(dto.getStoreId()).build())
                .totalOrderPrice(totalPrice)
                .userEntity(UserEntity.builder().userId(facade.getLoginUserPk()).build())
                .build();

        orderRep.save(entity);
        List<OrderDetailEntity> orderDetailEntityList = (List<OrderDetailEntity>) cartProductList.stream().map(item -> OrderDetailEntity.builder()
                .orderEntity(entity)
                .productEntity(ProductEntity.builder().productId(item.getProductId()).build())
                .quantity(item.getQuantity())
                .salePrice(item.getPrice() * item.getQuantity())
                .build()).toList();
        orderDetailRep.saveAll(orderDetailEntityList);

        //구매 후 카트 삭제
        cartRep.deleteByUserEntity(UserEntity.builder().userId(facade.getLoginUserPk()).build());

        return entity.getOrderId();
    }


    @Transactional
    public long insEachPayment(EachPaymentInsDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        SaleProductVo saleProductVo = dao.selProduct(dto.getProductId());
        if(saleProductVo == null) { throw new RestApiException(PaymentErrorCode.NO_PRODUCT); }

        int totalPrice = saleProductVo.getPrice() * dto.getQuantity();
        OrderEntity entity = OrderEntity.builder()
                .orderStatus(1)
                .orderDate(LocalDateTime.now())
                .payment(1)
                .pickupTime(LocalDateTime.parse(dto.getPickupTime(), formatter))
                .storeEntity(StoreEntity.builder().storeId(dto.getStoreId()).build())
                .totalOrderPrice(totalPrice)
                .userEntity(UserEntity.builder().userId(facade.getLoginUserPk()).build())
                .build();

        orderRep.save(entity);

        List<OrderDetailEntity> orderDetailEntityList = new ArrayList();
        orderDetailEntityList.add(
                OrderDetailEntity.builder().orderEntity(entity)
                .productEntity(ProductEntity.builder().productId(dto.getProductId()).build())
                .quantity(dto.getQuantity())
                .salePrice(saleProductVo.getPrice() * dto.getQuantity())
                .build()
        );
        orderDetailRep.saveAll(orderDetailEntityList);

        return entity.getOrderId();
    }

    public int updPayment(PaymentUpdDto dto) {
        OrderEntity orderEntity = orderRep.findById(dto.getOrderId()).orElseThrow(() -> new RestApiException(PaymentErrorCode.NO_ORDER));
        orderEntity.setOrderStatus(dto.getOrderStatus());
        orderRep.save(orderEntity);
        return 1;
    }

    public long insReview(ReviewInsDto dto) {
        ReviewEntity entity = ReviewEntity.builder()
                .orderDetailEntity(OrderDetailEntity.builder().orderDetailId(dto.getOrderDetailId()).build())
                .reviewLevel(dto.getReviewLevel())
                .build();
        reviewRep.save(entity);
        return entity.getReviewId();
    }

    public List<RegionSelVo> selRegion() {
        return dao.selRegion(facade.getLoginUserPk());
    }
}
