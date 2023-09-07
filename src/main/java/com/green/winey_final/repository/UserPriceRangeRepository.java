package com.green.winey_final.repository;

import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.common.entity.UserPriceRangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserPriceRangeRepository extends JpaRepository<UserPriceRangeEntity,Long> {
    List<UserPriceRangeEntity> findPriceRangeByUserEntity(UserEntity userId);
    void deleteAllByUserEntity(UserEntity userId);
}
