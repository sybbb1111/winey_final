package com.green.winey_final.recommend;


import com.green.winey_final.common.entity.UserRecommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<UserRecommendEntity,Long> {

}
