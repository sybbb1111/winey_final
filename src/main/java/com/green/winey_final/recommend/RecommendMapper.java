package com.green.winey_final.recommend;


import com.green.winey_final.recommend.model.RecommendRes;
import com.green.winey_final.recommend2.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecommendMapper {
    List<Long> selRecommend(RecommendRes res);
}
