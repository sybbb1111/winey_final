package com.green.winey_final.repository;

import com.green.winey_final.common.entity.CartEntity;
import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    CartEntity findByUserEntityAndProductEntity(UserEntity userEntity, ProductEntity productEntity);
}
