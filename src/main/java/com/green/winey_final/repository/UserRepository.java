package com.green.winey_final.repository;

import com.green.winey_final.common.config.security.model.ProviderType;
import com.green.winey_final.common.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //소셜로그인 회원
    UserEntity findByProviderTypeAndUid(ProviderType providerType, String uid);
    UserEntity findByProviderTypeAndUidAndDelYn(ProviderType providerType, String uid, Long delYn);
    //delYn여부 추가, 0일 시에만 로그인가능, 1일시에는 탈퇴회원으로 로그인 불가


    //로컬 회원가입 회원
    //로컬회원한정, 가입 이메일로 userId 찾기 (임시 비밀번호로 업데이트하기 위함)
    UserEntity findByProviderTypeAndEmail(ProviderType providerType, String email);
    UserEntity findByProviderTypeAndEmailAndDelYn(ProviderType providerType, String email, Long delYn);
    //delYn여부 추가, 0일 시에만 로그인가능, 1일시에는 탈퇴회원으로 로그인 불가


    UserEntity findByUserIdAndDelYn(Long userId, Long delYn);

    //로컬 회원 이메일 중복 체크
    boolean existsByProviderTypeAndEmail(ProviderType providerType, String email);

    UserEntity findByUnmAndTel(String unm, String tel);



}
