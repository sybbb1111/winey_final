package com.green.winey_final.main2;

import com.green.winey_final.main2.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainMapper2 {



    int updPrice(WineUpdDto2 dto);
    int updSalePrice(WineUpdSalePriceDto2 dto);

    List<WineTotalVo2> redWine(WineSelDto2 dto);
    List<WineTotalVo2> redWineByNew(WineSelDto2 dto);
    List<WineTotalVo2> redWineByExpencive(WineSelDto2 dto);
    List<WineTotalVo2> redWineByCheap(WineSelDto2 dto);

    List<WineTotalVo2> whiteWine(WineSelDto2 dto);
    List<WineTotalVo2> whiteWineByNew(WineSelDto2 dto);
    List<WineTotalVo2> whiteWineByExpencive(WineSelDto2 dto);
    List<WineTotalVo2> whiteWineByCheap(WineSelDto2 dto);

    List<WineTotalVo2> sparklingWine(WineSelDto2 dto);
    List<WineTotalVo2> sparklingWineByNew(WineSelDto2 dto);
    List<WineTotalVo2> sparklingWineByExpencive(WineSelDto2 dto);
    List<WineTotalVo2> sparklingWineByCheap(WineSelDto2 dto);

    List<WineTotalVo2> otherWine(WineSelDto2 dto);
    List<WineTotalVo2> otherWineByNew(WineSelDto2 dto);
    List<WineTotalVo2> otherWineByExpencive(WineSelDto2 dto);
    List<WineTotalVo2> otherWineByCheap(WineSelDto2 dto);

    /*WineFeatureVo beginners(WineFeatureDto dto);*/
    List<WineTotalVo2> selWine(WineSelDto2 dto);
    List<WineTotalVo2> selWinePrice();
    List<WineTotalVo2> selWineByNew(WineSelDto2 dto);
    List<WineTotalVo2> selWineByExpencive(WineSelDto2 dto);
    List<WineTotalVo2> selWineByCheap(WineSelDto2 dto);

    //    List<WineVo> selWineByPrice(WineSelByPriceDto dto);
    WineTotalVo2 selWineById(WineSelDetailDto2 dto);

    List<WineTotalVo2> selWineByPrice2(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice2New(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice2Expencive(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice2Cheap(WineSelDto2 dto);

    List<WineTotalVo2> selWineByPrice25(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice25New(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice25Expencive(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice25Cheap(WineSelDto2 dto);

    List<WineTotalVo2> selWineByPrice510(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice510New(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice510Expencive(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice510Cheap(WineSelDto2 dto);

    List<WineTotalVo2> selWineByPrice10(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice10New(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice10Expencive(WineSelDto2 dto);
    List<WineTotalVo2> selWineByPrice10Cheap(WineSelDto2 dto);

    List<WineTotalVo2> selWineByCountry(WineSelByCountryDto2 dto);
    List<WineTotalVo2> selWineByCountryNew(WineSelByCountryDto2 dto);
    List<WineTotalVo2> selWineByCountryExpencive(WineSelByCountryDto2 dto);
    List<WineTotalVo2> selWineByCountryCheap(WineSelByCountryDto2 dto);

    List<WineFoodVo2> selWineByFood(WineSelByFoodDto2 dto);
    List<WineFoodVo2> selWineByFoodNew(WineSelByFoodDto2 dto);
    List<WineFoodVo2> selWineByFoodExpencive(WineSelByFoodDto2 dto);
    List<WineFoodVo2> selWineByFoodCheap(WineSelByFoodDto2 dto);


}
