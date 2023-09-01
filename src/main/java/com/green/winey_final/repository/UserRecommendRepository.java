package com.green.winey_final.repository;

import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.common.entity.UserRecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRecommendRepository extends JpaRepository<UserRecommendEntity, Long> {

    List<UserRecommendEntity> findByUserEntity(UserEntity userId);
    List<UserRecommendEntity> findCountryIdByUserEntity(UserEntity userId);
    List<UserRecommendEntity> findSmallCategoryIdIdByUserEntity(UserEntity userId);
    List<UserRecommendEntity> findAromaCategoryIdByUserEntity(UserEntity userId);
    List<UserRecommendEntity> findPriceRangeByUserEntity(UserEntity userId);

}



