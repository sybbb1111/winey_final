package com.green.winey_final.repository;

import com.green.winey_final.common.config.security.model.ProviderType;
import com.green.winey_final.common.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByProviderTypeAndUid(ProviderType providerType, String uid);
}
