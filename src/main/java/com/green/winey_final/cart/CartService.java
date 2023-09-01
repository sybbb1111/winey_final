package com.green.winey_final.cart;

import com.green.winey_final.cart.model.*;
import com.green.winey_final.common.config.exception.CartErrorCode;
import com.green.winey_final.common.config.exception.RestApiException;
import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.CartEntity;
import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {
    private final AuthenticationFacade facade;
    private final CartRepository rep;
    private final int CART_MAX_QUENTITY = 5;

    public int insCart(CartInsDto dto) { //장바구니 추가
        if(dto.getQuantity() > CART_MAX_QUENTITY) {
            throw new RestApiException(CartErrorCode.QUANTITY_VALUE_OVER);
        }
        UserEntity userEntity = UserEntity.builder().userId(facade.getLoginUserPk()).build();
        ProductEntity productEntity = ProductEntity.builder().productId(dto.getProductId()).build();
        CartEntity managedCardEntity = rep.findByUserEntityAndProductEntity(userEntity, productEntity);

        if(managedCardEntity == null) {
            CartEntity pCartEntity = CartEntity.builder()
                    .userEntity(userEntity)
                    .productEntity(productEntity)
                    .quantity(dto.getQuantity())
                    .build();
            rep.save(pCartEntity);
            return dto.getQuantity();
        }
        int willSaveQuantity = managedCardEntity.getQuantity() + dto.getQuantity();
        if(willSaveQuantity > CART_MAX_QUENTITY) { //저장될 수량이 5초과이라면
            throw new RestApiException(CartErrorCode.QUANTITY_OVER);
        }
        managedCardEntity.setQuantity(willSaveQuantity);
        rep.save(managedCardEntity);
        return willSaveQuantity;
    }

    public List<CartVo> selCart() { //장바구니 출력
        CartSelDto dto = new CartSelDto();
        dto.setUserId(facade.getLoginUserPk());
        //return mapper.selCart(dto);
        return null;
    }

    public int delCart(CartdelDto dto) { //장바구니 삭제
        return 0;
    }

    public int updCart(CartUpdDto dto) { //장바구니 업데이트
        return 0;
    }
}
