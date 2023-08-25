package com.green.winey_final.recommend;

import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.common.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendService {
    private final RecommendRepository recommendRep;
    private final UserInfoEntityRepository wineRepository;

    public List<Long> selUserinfo(Long userId) {
        UserEntity entity =new UserEntity();
        entity.setUserId(userId);
        List<UserInfoEntity> list = wineRepository.findByUserEntity(entity);
        List<Long> productPK=new ArrayList<>();


        for (UserInfoEntity userInfoEntity : list) {
            productPK.add(userInfoEntity.getProductEntity().getProductId());
        }return productPK;
        /*
        목표값: 1번유저의 추천상품PK값만
        필요한값: 유저의 PK값
        방법: 유저의 PK값을 입력하면 상품PK리스트로 값을 보여준다.
         */


    }


//        UserEntity entity1 = UserEntity.builder()
//                            .iuser(1L)
//                            .build();



    }

