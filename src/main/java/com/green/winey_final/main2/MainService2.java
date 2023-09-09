package com.green.winey_final.main2;

import com.green.winey_final.main2.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainService2 {

    private final MainMapper2 MAPPER;

    public CountRes redWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineRed(dto);

        List<WineTotalVo2> wine = MAPPER.redWine(dto);
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
            vo.setSale(0);
            vo.setSalePrice(vo.getPrice());
            }
        }

        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes redWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineRed(dto);

        List<WineTotalVo2> wine = MAPPER.redWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }

        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes redWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineRed(dto);

        List<WineTotalVo2> wine = MAPPER.redWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }

        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes redWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineRed(dto);

        List<WineTotalVo2> wine = MAPPER.redWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }

        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    //====================================================================

    public CountRes whiteWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineWhite(dto);

        List<WineTotalVo2> wine = MAPPER.whiteWine(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();

    }

    public CountRes whiteWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineWhite(dto);

        List<WineTotalVo2> wine = MAPPER.whiteWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes whiteWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineWhite(dto);

        List<WineTotalVo2> wine = MAPPER.whiteWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes whiteWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineWhite(dto);

        List<WineTotalVo2> wine = MAPPER.whiteWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    //====================================================================

    public CountRes sparklingWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSparkling(dto);

        List<WineTotalVo2> wine = MAPPER.sparklingWine(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes sparklingWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSparkling(dto);

        List<WineTotalVo2> wine = MAPPER.sparklingWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();

    }

    public CountRes sparklingWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSparkling(dto);

        List<WineTotalVo2> wine = MAPPER.sparklingWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes sparklingWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSparkling(dto);

        List<WineTotalVo2> wine = MAPPER.sparklingWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    //====================================================================

    public CountRes otherWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineOther(dto);

        List<WineTotalVo2> wine = MAPPER.otherWine(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();

    }

    public CountRes otherWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineOther(dto);

        List<WineTotalVo2> wine = MAPPER.otherWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();

    }

    public CountRes otherWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineOther(dto);

        List<WineTotalVo2> wine = MAPPER.otherWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes otherWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineOther(dto);

        List<WineTotalVo2> wine = MAPPER.otherWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    //====================================================================

    // 금액 10원단위 절삭
    public List<WineTotalVo2> selWinePrice() {
        List<WineTotalVo2> voList = MAPPER.selWinePrice();

        for (WineTotalVo2 vo : voList) {
            double originalPrice = vo.getPrice();
            int ceilPrice = (int) Math.ceil(originalPrice / 100) * 100;

            WineUpdDto2 dto = new WineUpdDto2();
            dto.setProductId(vo.getProductId());
            dto.setPrice(ceilPrice);
            MAPPER.updPrice(dto);
        }

        return MAPPER.selWinePrice();
    }

    //더미데이터 세일금액으로 변경
    public List<WineTotalVo2> selWineSalePrice() {
        List<WineTotalVo2> voList = MAPPER.selWinePrice();

        for (WineTotalVo2 vo : voList) {
            double originalPrice = vo.getPrice();
            int sale = vo.getSale();
            int salePrice = (int) Math.ceil(originalPrice-(originalPrice / sale));
            int ceilPrice = (int) Math.ceil(salePrice / 100) * 100;

            WineUpdSalePriceDto2 dto = new WineUpdSalePriceDto2();
            dto.setProductId(vo.getProductId());
            dto.setSalePrice(ceilPrice);
            MAPPER.updSalePrice(dto);
        }

        return MAPPER.selWinePrice();
    }
//====================================================================

    public CountRes selWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel(dto);

        List<WineTotalVo2> wine = MAPPER.selWine(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }


    public CountRes selWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    //    public List<WineVo> selWineByPrice(WineSelByPriceDto dto) {
//        return MAPPER.selWineByPrice(dto);
//    }

    public WineTotalVo2 selWineById(WineSelDetailDto2 dto) {
        return MAPPER.selWineById(dto);
    }
    //====================================================================

    public CountRes selWineByPrice2(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel2(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice2(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice2New(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel2(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice2New(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice2Expencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel2(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice2Expencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice2Cheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel2(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice2Cheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }
    //====================================================================

    public CountRes selWineByPrice25(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel25(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice25(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice25New(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel25(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice25New(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice25Expencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel25(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice25Expencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice25Cheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel25(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice25Cheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }
    //====================================================================

    public CountRes selWineByPrice510(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel510(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice510(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice510New(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel510(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice510New(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice510Expencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel510(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice510Expencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice510Cheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel510(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice510Cheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    //====================================================================

    public CountRes selWineByPrice10(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel10(dto);

        List<WineTotalVo2> wine =  MAPPER.selWineByPrice10(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice10New(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel10(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice10New(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice10Expencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel10(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice10Expencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByPrice10Cheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineSel10(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByPrice10Cheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    //===================================================================================
    public CountRes selWineByCountry(WineSelByCountryDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineCountry(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByCountry(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByCountryNew(WineSelByCountryDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineCountry(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByCountryNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByCountryExpencive(WineSelByCountryDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineCountry(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByCountryExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountRes selWineByCountryCheap(WineSelByCountryDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineCountry(dto);

        List<WineTotalVo2> wine = MAPPER.selWineByCountryCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

//===================================================================================

    public CountFoodRes selWineByFood(WineSelByFoodDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineFood(dto);

        List<WineFoodVo2> wine = MAPPER.selWineByFood(dto);
        for (WineFoodVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
//                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountFoodRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountFoodRes selWineByFoodNew(WineSelByFoodDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineFood(dto);

        List<WineFoodVo2> wine = MAPPER.selWineByFoodNew(dto);
        for (WineFoodVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountFoodRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountFoodRes selWineByFoodExpencive(WineSelByFoodDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineFood(dto);

        List<WineFoodVo2> wine = MAPPER.selWineByFoodExpencive(dto);
        for (WineFoodVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountFoodRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }

    public CountFoodRes selWineByFoodCheap(WineSelByFoodDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        int result = MAPPER.countWineFood(dto);

        List<WineFoodVo2> wine = MAPPER.selWineByFoodCheap(dto);
        for (WineFoodVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return CountFoodRes.builder()
                .count(result)
                .wineList(wine)
                .build();
    }
}
