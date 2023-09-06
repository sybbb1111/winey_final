package com.green.winey_final.repository;

import com.green.winey_final.common.entity.UserAromaEntity;
import com.green.winey_final.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAromaRepository extends JpaRepository<UserAromaEntity,Long> {
    UserAromaEntity findByUserEntity(UserEntity userId);
    List<UserAromaEntity> findAromaCategoryIdByUserEntity(UserEntity userId);


}
