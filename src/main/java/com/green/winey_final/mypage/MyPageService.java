package com.green.winey_final.mypage;

import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.RegionNmEntity;
import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.mypage.model.UserRes;
import com.green.winey_final.mypage.model.UserUpdDto;
import com.green.winey_final.repository.RegionNmRepository;
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
    private final RegionNmRepository regionNmRep;

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

    public UserRes putUserInfo(UserUpdDto dto) {

        RegionNmEntity regionNmEntity = regionNmRep.findById(dto.getRegionNmId()).get();

        UserEntity orgEntity = userRep.findById(facade.getLoginUser().getUserId()).get();

        UserEntity updEntity = UserEntity.builder()
                .userId(facade.getLoginUser().getUserId())
                .providerType(orgEntity.getProviderType())
                .roleType(orgEntity.getRoleType())
                .uid(orgEntity.getUid())
                .upw(orgEntity.getUpw())
                .email(orgEntity.getEmail())

                .tosYn(orgEntity.getTosYn())
                .delYn(orgEntity.getDelYn())

                .unm(dto.getUnm())
                .tel(dto.getTel())
                .regionNmEntity(regionNmEntity)

                .build();

        userRep.save(updEntity);



        return UserRes.builder()
                .userId(updEntity.getUserId())
                .email(updEntity.getEmail())
                .unm(updEntity.getUnm())
                .tel(updEntity.getTel())

                .regionNmId(updEntity.getRegionNmEntity().getRegionNmId())
                .regionNm(updEntity.getRegionNmEntity().getRegionNm())

                .del_yn(updEntity.getDelYn())

                .providerType(updEntity.getProviderType())
                .roleType(updEntity.getRoleType())

                .build();
    }

    public void delUser() {
        UserEntity userEntity = userRep.findById(facade.getLoginUser().getUserId()).get();

        userEntity.setDelYn(1L);

        userRep.save(userEntity);



    }




}
