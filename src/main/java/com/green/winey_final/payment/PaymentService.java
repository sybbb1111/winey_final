package com.green.winey_final.payment;

import com.green.winey_final.common.config.exception.PaymentErrorCode;
import com.green.winey_final.common.config.exception.RestApiException;
import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.*;
import com.green.winey_final.payment.model.CartProductVo;
import com.green.winey_final.payment.model.PaymentInsDto;
import com.green.winey_final.repository.OrderDetailRepository;
import com.green.winey_final.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Transactional
    public long insPayment(PaymentInsDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<CartProductVo> cartProductList = dao.selCartProduct(facade.getLoginUserPk());
        if(cartProductList.size() == 0) {
            throw new RestApiException(PaymentErrorCode.NO_PRODUCT_IN_CART);
        }

        Optional<Integer> optTotalPrice = cartProductList.stream().map(item -> item.getPrice()).reduce((x, y) -> x + y);
        int totalPrice = optTotalPrice.isPresent() ? optTotalPrice.get() : 0;

        OrderEntity entity = OrderEntity.builder()
                .orderStatus(1)
                .orderTime(LocalDateTime.now())
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
                .salePrice(item.getPrice())
                .build()).toList();
        orderDetailRep.saveAll(orderDetailEntityList);
        return entity.getOrderId();
    }
}
