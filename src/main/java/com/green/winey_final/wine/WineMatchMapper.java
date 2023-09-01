package com.green.winey_final.wine;



import com.green.winey_final.wine.model.Match;
import com.green.winey_final.wine.model.WineInsDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WineMatchMapper {

    int insWine(WineInsDto dto);
    void saveMatch(Match match);
    List<Match> getAllMatches();

}