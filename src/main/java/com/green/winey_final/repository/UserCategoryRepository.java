package com.green.winey_final.repository;

import com.green.winey_final.common.entity.UserCategoryEntity;
import com.green.winey_final.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCategoryRepository extends JpaRepository<UserCategoryEntity,Long> {
    UserCategoryEntity findByUserEntity(UserEntity userId);
    List<UserCategoryEntity> findCategoryIdByUserEntity(UserEntity userId);
}
