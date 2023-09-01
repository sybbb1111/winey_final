package com.green.winey_final.recommend2;


import com.green.winey_final.recommend2.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecommendMapper2 {
    List<Long> selRecommend(RecommendRes2 res);
    int insUserinfo(UserinfoDto2 dto);
    int insRecommend(InsRecommendDto2 res);
    int loginUserPk(UserDto2 dto);
    List<Integer> selUserinfo(SelRecommendDto2 dto);
    List<Long> selUserCategoryId(UserDto2 dto);
    List<Long> selUserCountryId(UserDto2 dto);
    List<Long> selUserSmallCategoryId(UserDto2 dto);
    List<Long> selUserPriceRange(UserDto2 dto);
    List<Long> selUserAroma(UserDto2 dto);
    int delUserRecommend(UserDto2 dto);
    int delInfo(UserDto2 dto);


}
