package com.green.winey_final.recommend;

import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.common.entity.*;
import com.green.winey_final.recommend.model.RecommendVo;
import com.green.winey_final.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendService {

    private final UserInfoEntityRepository wineRepository;
    private final AuthenticationFacade facade;
    private final UserCategoryRepository userCategory;
    private final UserCountryRepository userCountry;
    private final UserPriceRangeRepository userPrice;
    private final UserSmallCategoryRepository userSmall;
    private final UserAromaRepository userAroma;


    public List<Long> selUserinfo() {
        Long userPk= facade.getLoginUserPk();
        UserEntity entity = new UserEntity();
        entity.setUserId(userPk);
        List<UserInfoEntity> list = wineRepository.findByUserEntity(entity);
        List<Long> productPK = new ArrayList<>();
        for (UserInfoEntity userInfoEntity : list) {
            productPK.add(userInfoEntity.getProductEntity().getProductId());
        }
        return productPK;

    }

    public RecommendVo selUserRecommend() {
        Long userPk = facade.getLoginUserPk();
        UserEntity entity = new UserEntity();
        entity.setUserId(userPk);

        List<Long> catelist = userCategory.findCategoryIdByUserEntity(entity).stream()
                .map(UserCategoryEntity::getCategoryId)
                .distinct()
                .collect(Collectors.toList());

        List<Long> coulist = userCountry.findCountryIdByUserEntity(entity).stream()
                .map(UserCountryEntity::getCountryId)
                .distinct()
                .collect(Collectors.toList());

        List<Long> smallCatelist = userSmall.findSmallCategoryIdByUserEntity(entity).stream()
                .map(UserSmallCategoryEntity::getSmallCategoryId)
                .distinct()
                .collect(Collectors.toList());

        List<Long> priceRalist = userPrice.findPriceRangeByUserEntity(entity).stream()
                .map(UserPriceRangeEntity::getPriceRange)
                .distinct()
                .collect(Collectors.toList());

        List<Long> aromalist = userAroma.findAromaCategoryIdByUserEntity(entity).stream()
                .map(UserAromaEntity::getAromaCategoryId)
                .distinct()
                .collect(Collectors.toList());

        return RecommendVo.builder()
                .categoryId(catelist)
                .countryId(coulist)
                .smallCategoryId(smallCatelist)
                .priceRange(priceRalist)
                .aromaCategoryId(aromalist)
                .build();
    }
//    public RecommendVo selUserRecommend(Long userId) {
//
////        UserEntity entity = new UserEntity();
////        entity.setUserId(userId);
////        List<UserRecommendEntity> list = userRecommendRepository.findByCategoryIdInCountryIdInSmallCategoryIdInPrcieRangeInAromaCategoryIdIn(entity);
////        RecommendVo recommendList = new RecommendVo();
////        for (UserRecommendEntity entity1 : list) {
////            RecommendVo recommendVo = RecommendVo.builder()
////                    .categoryId(entity1.getCategoryId())
////                    .countryId(entity1.getCountryId())
////                    .smallCategoryId(entity1.getSmallCateGoryId())
////                    .priceRange(entity1.getPriceRange())
////                    .aromaCategoryId(entity1.getAromaCategoryId())
////                    .build();
//
//
////            recommendList.add(recommendVo);
////        }
//        List<UserRecommendEntity> list = userRecommendRepository.findAllByUserEntity(UserEntity.builder()
//                .userId(userId)
//                .build());
//        log.info("list : {}", list);
//        return null;
    }


//        UserEntity item = UserEntity.builder()
//                            .iuser(1L)
//                            .build();




