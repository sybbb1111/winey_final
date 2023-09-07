package com.green.winey_final.repository;

import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.common.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfoEntity,Long> {
    List<UserInfoEntity>findByUserEntity(UserEntity userId);
    void deleteAllByUserEntity(UserEntity userId);
}
