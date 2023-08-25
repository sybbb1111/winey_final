package com.green.winey_final.repository;

import com.green.winey_final.common.entity.UserId;
import com.green.winey_final.common.entity.UserTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTokenRepository extends JpaRepository<UserTokenEntity, UserId> {
}
