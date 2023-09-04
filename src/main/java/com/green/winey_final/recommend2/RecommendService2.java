package com.green.winey_final.recommend2;


import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.recommend2.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.util.CollectionUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecommendService2 {
    private final RecommendMapper2 mapper;
    private final AuthenticationFacade facade;

    public List<Long> selRecommend(RecommendRes2 res) {
        List<Long> result = mapper.selRecommend(res);
        UserinfoDto2 dto = new UserinfoDto2();
        dto.setUserId(facade.getLoginUserPk());
        dto.setProductId(result);
        InsRecommendDto2 res2 = new InsRecommendDto2();
        res2.setUserId(facade.getLoginUserPk());


        int size =0;
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        int size4 = 0;
        if (CollectionUtils.isEmpty(res.getCategoryId())==true) {
             size = 0;
        } else {
            size = res.getCategoryId().size();
        }
        if (CollectionUtils.isEmpty(res.getCountryId())==true) {
             size1 = 0;
        }else {
            size1 =res.getCountryId().size();
        }
        if (CollectionUtils.isEmpty(res.getSmallCategoryId())==true) {
             size2 = 0;
        }else {
            size2=res.getSmallCategoryId().size();
        }
        if (CollectionUtils.isEmpty(res.getPriceRange())==true) {
             size3 = 0;
        }else{size3 = res.getPriceRange().size();}
        if (CollectionUtils.isEmpty(res.getAromaCategoryId())==true) {
            size4 = 0;
        }else{size4 = res.getAromaCategoryId().size();}

        //1번쨰 2번째 3번쨰
        int[] listSizeArr = {
                size,
                size1,
                size2,
                size3,
                size4
        }; // 각 루프의 반복 횟수 설정
        //첫번째에는 1번째 list의 .size 들어감
        //두번째에는 2번째 list의 .size 들어감
        generateNestedLoops(listSizeArr, 0, res2,res);
        mapper.insUserinfo(dto);
        return result;
    }

    public void generateNestedLoops(int[] listSizeArr, int loopIndex, InsRecommendDto2 res2,
                                    RecommendRes2 res) {
        if (loopIndex + 1 == listSizeArr.length) {      // 내부 루프의 가장 안쪽에서 실행될 코드
            if (listSizeArr[loopIndex] > 0) {
                for (int i = 0; i < listSizeArr[loopIndex]; i++) {
                    System.out.printf("%d번째 list for문 %d번 작동\n", loopIndex + 1, i + 1);
                    res2.setAromaCategoryId(res.getAromaCategoryId().get(i));
                    mapper.insRecommend(res2);
                }
            } else if (listSizeArr[loopIndex] == 0) {
                System.out.printf("%d번째 list 작동안함\n", loopIndex + 1);
                mapper.insRecommend(res2);
            }
            System.out.printf("끝도착\n", loopIndex);
            return;
        }
        if (listSizeArr[loopIndex] > 0) {
            for (int i = 0; i < listSizeArr[loopIndex]; i++) {
                System.out.printf("%d번째 list for문 %d번 작동\n", loopIndex + 1, i + 1);
                if(loopIndex == 0) {
                    res2.setCategoryId(res.getCategoryId().get(i));
                } else if (loopIndex == 1) {
                    res2.setCountryId(res.getCountryId().get(i));
                } else if (loopIndex == 2) {
                    res2.setSmallCategoryId(res.getSmallCategoryId().get(i));
                } else if (loopIndex == 3) {
                    res2.setPriceRange(res.getPriceRange().get(i));
                } else if (loopIndex==4) {
                    res2.setAromaCategoryId(res.getAromaCategoryId().get(i));
                }
                generateNestedLoops(listSizeArr, loopIndex + 1, res2, res); // 다음 루프로 재귀 호출
            }
        } else if (listSizeArr[loopIndex] == 0) {
            System.out.printf("%d번째 list 작동안함\n", loopIndex + 1);
            if(loopIndex == 0) {
                res2.setCategoryId(null);
            } else if (loopIndex == 1) {
                res2.setCountryId(null);
            } else if (loopIndex == 2) {
                res2.setSmallCategoryId(null);
            } else if (loopIndex== 3) {
                res2.setPriceRange(null);
            } else if (loopIndex==4){
                res2.setAromaCategoryId(null);
            }
            generateNestedLoops(listSizeArr, loopIndex + 1, res2, res);
        }
    }

    public int loginUserPk() {
        UserDto2 dto = new UserDto2();
        dto.setUserId(facade.getLoginUserPk());
        return mapper.loginUserPk(dto);
    }

    public List<Integer> selUserinfo() {
        SelRecommendDto2 dto = new SelRecommendDto2();
        dto.setUserId(facade.getLoginUserPk());
        return mapper.selUserinfo(dto);
    }

    public RecommendVo2 selUserRecommend(){
        UserDto2 dto =new UserDto2();
        dto.setUserId(facade.getLoginUserPk());
         List<Long> categoryId=mapper.selUserCategoryId(dto);
         List<Long> countryId=mapper.selUserCountryId(dto);
         List<Long> smallCategoryId=mapper.selUserSmallCategoryId(dto);
         List<Long> priceRange=mapper.selUserPriceRange(dto);
         List<Long> aroma=mapper.selUserAroma(dto);
        return RecommendVo2.builder()
                .categoryId(categoryId)
                .countryId(countryId)
                .smallCategoryId(smallCategoryId)
                .priceRange(priceRange)
                .aromaCategoryId(aroma)
                .build();
    }
    public List<Long> updRecommend(RecommendRes2 res) {
        UserDto2 dto1 =new UserDto2();
        dto1.setUserId(facade.getLoginUserPk());
        mapper.delInfo(dto1);
        mapper.delUserRecommend(dto1);
        List<Long> result = mapper.selRecommend(res);
        UserinfoDto2 dto = new UserinfoDto2();
        dto.setUserId(facade.getLoginUserPk());
        dto.setProductId(result);
        InsRecommendDto2 res2 = new InsRecommendDto2();
        res2.setUserId(facade.getLoginUserPk());
        int size =0;
        int size1 = 0;
        int size2 = 0;
        int size3 = 0;
        int size4 = 0;
        if (CollectionUtils.isEmpty(res.getCategoryId())==true) {
            size = 0;
        } else {
            size = res.getCategoryId().size();
        }
        if (CollectionUtils.isEmpty(res.getCountryId())==true) {
            size1 = 0;
        }else {
            size1 =res.getCountryId().size();
        }
        if (CollectionUtils.isEmpty(res.getSmallCategoryId())==true) {
            size2 = 0;
        }else {
            size2=res.getSmallCategoryId().size();
        }
        if (CollectionUtils.isEmpty(res.getPriceRange())==true) {
            size3 = 0;
        }else{size3 = res.getPriceRange().size();}
        if (CollectionUtils.isEmpty(res.getAromaCategoryId())==true) {
            size4 = 0;
        }else{size4 = res.getAromaCategoryId().size();}

        //1번쨰 2번째 3번쨰
        int[] listSizeArr = {
                size,
                size1,
                size2,
                size3,
                size4
        }; // 각 루프의 반복 횟수 설정
        //첫번째에는 1번째 list의 .size 들어감
        //두번째에는 2번째 list의 .size 들어감
        generateNestedLoops(listSizeArr, 0, res2,res);
        mapper.insUserinfo(dto);
        return result;
    }


}
