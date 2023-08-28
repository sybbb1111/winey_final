package com.green.winey_final.main;


import com.green.winey_final.main.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper {

    List<WineVo> searchWine(WineSearchDto dto);

    int selLastWine(WineSearchDto dto);


}
