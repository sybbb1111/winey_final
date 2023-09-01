package com.green.winey_final.main2.six;

import com.green.winey_final.main2.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainSixMapper2 {
    //6개씩 출력
    List<WineTotalVo2> selWineByPrice2limit6();
    List<WineTotalVo2> selWineByPrice25limit6();
    List<WineTotalVo2> selWineByPrice510limit6();
    List<WineTotalVo2> selWineByPrice10limit6();
    List<WineTotalVo2> selWineByCountrylimit6(WineSelByCountryDto2 dto);
    List<WineFoodVo2> selWineByFoodlimit6(WineSelByFoodDto2 dto);

    List<WineRecommendVo2> selWineByday(Long val);

}
