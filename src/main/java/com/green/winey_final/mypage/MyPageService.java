package com.green.winey_final.mypage;


import com.green.winey_final.common.entity.RegionNmEntity;
import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.mypage.model.SelUserVo;
import com.green.winey_final.mypage.model.UpduserDto2;
import com.green.winey_final.repository.UserInfoRepostory;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final UserInfoRepostory userInfoRepostory;
    private final RegionNmRepository regionNmRepository;
    public int updUser(UpduserDto2 dto) {
        Optional<UserEntity> optEntity = userInfoRepostory.findById(dto.getUserId());
        RegionNmEntity regionNmEntity = regionNmRepository.findById(dto.getRegionNmId()).orElseThrow(() -> new EntityNotFoundException("RegionNmEntity not found with id: " + dto.getRegionNmId()));
        UserEntity entity = optEntity.get();
        entity.setUserId(dto.getUserId());
        entity.setNm(dto.getName());
        entity.setTel(dto.getTel());
        entity.setTel(dto.getPw());
        entity.setRegionNm(regionNmEntity);
        UserEntity userEntity=userInfoRepostory.save(entity);
        // userId처리 해줘야함 dot2 사용해서해야함 왜냐하면 지금은 시큐리티에서 유저아이디를 못빼와서 맞춘거임
        return 1;
    }

    public SelUserVo selUser(Long userId){
        Optional<UserEntity> optEntity = userInfoRepostory.findById(userId);
        UserEntity entity =optEntity.get();
        if(optEntity.isEmpty()) return null;
        return SelUserVo.builder()
                .userId(userId)
                .email(entity.getEmail())
                .nm(entity.getNm())
                .tel(entity.getTel())
                .regionNm(entity.getRegionNm().getRegionNmId())
                .delYn(entity.getDel_yn())
                .build();
    }

    public int delUser(Long userId){
        Optional<UserEntity> optEntity = userInfoRepostory.findById(userId);
        UserEntity entity = optEntity.get();
        entity.setDel_yn(1L);
        userInfoRepostory.save(entity);
        return 1;
    }

}
