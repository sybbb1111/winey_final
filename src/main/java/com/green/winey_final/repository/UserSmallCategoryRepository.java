package com.green.winey_final.repository;

import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.common.entity.UserSmallCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSmallCategoryRepository extends JpaRepository<UserSmallCategoryEntity,Long> {
    UserSmallCategoryEntity findByUserEntity(UserEntity userId);
    List<UserSmallCategoryEntity> findSmallCategoryIdByUserEntity(UserEntity userId);
}
