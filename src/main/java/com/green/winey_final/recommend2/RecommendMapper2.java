package com.green.winey_final.recommend2;


import com.green.winey_final.recommend2.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecommendMapper2 {
    List<Long> selRecommend(RecommendRes2 res);
    Long insUserCategory(UserCategory category);
    Long insUserCountry(UserCountry country);
    Long insUserPriceRange(UserPriceRange priceRange);
    Long insUserSmallCateogry(UserSmallCategory smallCategory);
    Long insUserAroma(UserAroma aroma);
    int insUserinfo(UserinfoDto2 dto);
    int loginUserPk(UserDto2 dto);
    List<Long> selUserinfo(SelRecommendDto2 dto);
    List<Long> selUserCategoryId(UserDto2 dto);
    List<Long> selUserCountryId(UserDto2 dto);
    List<Long> selUserSmallCategoryId(UserDto2 dto);
    List<Long> selUserPriceRange(UserDto2 dto);
    List<Long> selUserAroma(UserDto2 dto);
    int delUserCategory(UserDto2 dto);
    int delUserCountry(UserDto2 dto);
    int delUserPriceRange(UserDto2 dto);
    int delUserSmallCategory(UserDto2 dto);
    int delUserAroma(UserDto2 dto);
    int delInfo(UserDto2 dto);


}
