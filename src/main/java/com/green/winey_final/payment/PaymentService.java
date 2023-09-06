package com.green.winey_final.payment;

import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.OrderEntity;
import com.green.winey_final.common.entity.StoreEntity;
import com.green.winey_final.payment.model.CartProductVo;
import com.green.winey_final.payment.model.PaymentInsDto;
import com.green.winey_final.repository.CartRepository;
import com.green.winey_final.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AuthenticationFacade facade;
    //private final PaymentRepository rep;
    private final PaymentDao dao;

    @Transactional
    public long insPayment(PaymentInsDto dto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        List<CartProductVo> cartProductList = dao.selCartProduct(facade.getLoginUserPk());
        //https://velog.io/@penrose_15/MySQL%EC%97%90%EC%84%9C-Bulk-Insert%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-%EC%9C%84%ED%95%9C-%EB%BB%98%EC%A7%93-%EB%AA%A8%EC%9D%8C
        long totalOrderPrice = 100L;

        OrderEntity entity = OrderEntity.builder()
                .orderStatus(1L)
                .orderDate(LocalDateTime.now())
                .payment(1L)
                .pickupTime(LocalDateTime.parse(dto.getPickupTime(), formatter))
                .storeEntity(StoreEntity.builder().storeId(dto.getStoreId()).build())
                .build();
        return 1L;
    }
}
