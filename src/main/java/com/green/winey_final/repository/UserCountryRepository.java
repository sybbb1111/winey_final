package com.green.winey_final.repository;

import com.green.winey_final.common.entity.UserCountryEntity;
import com.green.winey_final.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCountryRepository extends JpaRepository<UserCountryEntity,Long> {
    List<UserCountryEntity> findCountryIdByUserEntity(UserEntity userId);
    void deleteAllByUserEntity(UserEntity userId);
}
