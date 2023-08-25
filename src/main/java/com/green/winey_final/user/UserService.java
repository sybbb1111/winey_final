package com.green.winey_final.user;

import com.green.winey_final.common.entity.RegionNmEntity;
import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.repository.RegionNmRepository;
import com.green.winey_final.repository.UserRepository;
import com.green.winey_final.user.model.UserRegDto;
import com.green.winey_final.user.model.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRep;
    private final RegionNmRepository regionNmRep;

    private final PasswordEncoder PW_ENCODER;

    public UserVo postUser(UserRegDto dto) {

        RegionNmEntity regionNmEntity = regionNmRep.findById(dto.getRegionNmId()).get();

        UserEntity entity = UserEntity.builder()
                .del_yn(dto.getDel_yn())
                .email(dto.getEmail())
                .providerType(dto.getProviderType())
                .roleType(dto.getRoleType())
                .tel(dto.getTel())
                .tos_yn(dto.getTos_yn())
                .unm(dto.getUnm())
                .upw(PW_ENCODER.encode(dto.getUpw()))
                .regionNmEntity(regionNmEntity)
                .build();

        userRep.save(entity);

        return UserVo.builder()
                .userId(entity.getUserId())
                .del_yn(entity.getDel_yn())
                .email(entity.getEmail())
                .providerType(entity.getProviderType())
                .roleType(entity.getRoleType())
                .tel(entity.getTel())
                .tos_yn(entity.getTos_yn())
                .unm(entity.getUnm())
                .upw(entity.getUpw())
                .regionNmId(entity.getRegionNmEntity().getRegionNmId())
                .build();
    }
}
