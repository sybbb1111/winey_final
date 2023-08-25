package com.green.winey_final.recommend;

import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.common.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoEntityRepository extends JpaRepository<UserInfoEntity,Long> {
    List<UserInfoEntity>findByUserEntity(UserEntity userId);
    List<UserInfoEntity>findByProductEntity(ProductEntity productId);
}
