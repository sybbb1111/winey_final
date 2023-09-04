package com.green.winey_final.recommend;

import com.green.winey_final.common.entity.UserEntity;
import com.green.winey_final.common.entity.UserInfoEntity;
import com.green.winey_final.common.entity.UserRecommendEntity;
import com.green.winey_final.recommend.model.RecommendVo;
import com.green.winey_final.repository.UserInfoEntityRepository;
import com.green.winey_final.repository.UserRecommendRepository;
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
    private final UserRecommendRepository userRecommendRepository;

    public List<Long> selUserinfo(Long userId) {
        UserEntity entity = new UserEntity();
        entity.setUserId(userId);
        List<UserInfoEntity> list = wineRepository.findByUserEntity(entity);
        List<Long> productPK = new ArrayList<>();
        for (UserInfoEntity userInfoEntity : list) {
            productPK.add(userInfoEntity.getProductEntity().getProductId());
        }
        return productPK;
        /*
        목표값: 1번유저의 추천상품PK값만
        필요한값: 유저의 PK값
        방법: 유저의 PK값을 입력하면 상품PK리스트로 값을 보여준다.
         */
    }

    public RecommendVo selUserRecommend(Long userId){
            UserEntity entity =new UserEntity();
            entity.setUserId(userId);

        List<UserRecommendEntity> categoryList = userRecommendRepository.findByUserEntity(entity);
        List<UserRecommendEntity> countryList = userRecommendRepository.findCountryIdByUserEntity(entity);
        List<UserRecommendEntity> smallCategoryList =userRecommendRepository.findSmallCategoryIdIdByUserEntity(entity);
        List<UserRecommendEntity> priceRangeList=userRecommendRepository.findPriceRangeByUserEntity(entity);
        List<UserRecommendEntity> aromaCategoryList=userRecommendRepository.findAromaCategoryIdByUserEntity(entity);


        List<Long> cateList = new ArrayList<>();
        List<Long> couList = new ArrayList<>();
        List<Long> smallCateList = new ArrayList<>();
        List<Long> priceRaList = new ArrayList<>();
        List<Long> aromaList = new ArrayList<>();

        for (UserRecommendEntity cate : categoryList) {
            cateList.add(cate.getCategoryId());
        }
        List<Long> catelist = cateList.stream().distinct().collect(Collectors.toList());

        for (UserRecommendEntity cou : countryList) {
            couList.add(cou.getCountryId());
        }
        List<Long> coulist = couList.stream().distinct().collect(Collectors.toList());

        for (UserRecommendEntity smallc : smallCategoryList) {
            smallCateList.add(smallc.getSmallCategoryId());
        }
        List<Long> smallCatelist = smallCateList.stream().distinct().collect(Collectors.toList());

        for (UserRecommendEntity priceR : priceRangeList) {
            priceRaList.add(priceR.getPriceRange());
        }
        List<Long> priceRalist = priceRaList.stream().distinct().collect(Collectors.toList());

        for (UserRecommendEntity aroma : aromaCategoryList) {
            aromaList.add(aroma.getAromaCategoryId());
        }
        List<Long> aromalist = aromaList.stream().distinct().collect(Collectors.toList());


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




