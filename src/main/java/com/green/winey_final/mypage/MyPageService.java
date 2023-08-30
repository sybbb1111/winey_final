package com.green.winey_final.mypage;

import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.mypage.model.UserRes;
import com.green.winey_final.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserRepository userRep;
    private final AuthenticationFacade facade;

    public UserRes getUserInfo() {

        UserEntity userEntity = userRep.findByUserIdAndDelYn(facade.getLoginUser().getUserId(), 0L);


        return UserRes.builder()
                .userId(userEntity.getUserId())
                .email(userEntity.getEmail())
                .unm(userEntity.getUnm())
                .tel(userEntity.getTel())

                .regionNmId(userEntity.getRegionNmEntity().getRegionNmId())
                .regionNm(userEntity.getRegionNmEntity().getRegionNm())

                .del_yn(userEntity.getDelYn())

                .providerType(userEntity.getProviderType())
                .roleType(userEntity.getRoleType())


                .build();

    }



}
