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




    public List<WineTotalVo2> redWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.redWine(dto);
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
            vo.setSale(0);
            vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> redWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.redWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> redWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.redWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> redWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.redWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    //====================================================================

    public List<WineTotalVo2> whiteWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.whiteWine(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> whiteWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.whiteWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> whiteWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.whiteWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> whiteWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.whiteWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    //====================================================================

    public List<WineTotalVo2> sparklingWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.sparklingWine(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }

    public List<WineTotalVo2> sparklingWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.sparklingWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> sparklingWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.sparklingWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> sparklingWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.sparklingWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    //====================================================================

    public List<WineTotalVo2> otherWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.otherWine(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> otherWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.otherWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> otherWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.otherWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> otherWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.otherWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
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


    public List<WineTotalVo2> selWine(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWine(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }


    public List<WineTotalVo2> selWineByNew(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByExpencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByCheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    //    public List<WineVo> selWineByPrice(WineSelByPriceDto dto) {
//        return MAPPER.selWineByPrice(dto);
//    }

    public WineTotalVo2 selWineById(WineSelDetailDto2 dto) {
        return MAPPER.selWineById(dto);
    }
    //====================================================================

    public List<WineTotalVo2> selWineByPrice2(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice2(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice2New(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice2New(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice2Expencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice2Expencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice2Cheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice2Cheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }
    //====================================================================

    public List<WineTotalVo2> selWineByPrice25(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice25(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }

    public List<WineTotalVo2> selWineByPrice25New(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());
        List<WineTotalVo2> wine = MAPPER.selWineByPrice25New(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice25Expencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());
        List<WineTotalVo2> wine = MAPPER.selWineByPrice25Expencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice25Cheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice25Cheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }
    //====================================================================

    public List<WineTotalVo2> selWineByPrice510(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice510(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice510New(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice510New(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice510Expencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice510Expencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice510Cheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice510Cheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    //====================================================================

    public List<WineTotalVo2> selWineByPrice10(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine =  MAPPER.selWineByPrice10(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice10New(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());
        List<WineTotalVo2> wine = MAPPER.selWineByPrice10New(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice10Expencive(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());
        List<WineTotalVo2> wine = MAPPER.selWineByPrice10Expencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByPrice10Cheap(WineSelDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByPrice10Cheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    //===================================================================================
    public List<WineTotalVo2> selWineByCountry(WineSelByCountryDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByCountry(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByCountryNew(WineSelByCountryDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByCountryNew(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineTotalVo2> selWineByCountryExpencive(WineSelByCountryDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByCountryExpencive(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }

    public List<WineTotalVo2> selWineByCountryCheap(WineSelByCountryDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineTotalVo2> wine = MAPPER.selWineByCountryCheap(dto);
        for (WineTotalVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

//===================================================================================

    public List<WineFoodVo2> selWineByFood(WineSelByFoodDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineFoodVo2> wine = MAPPER.selWineByFood(dto);
        for (WineFoodVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineFoodVo2> selWineByFoodNew(WineSelByFoodDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineFoodVo2> wine = MAPPER.selWineByFoodNew(dto);
        for (WineFoodVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineFoodVo2> selWineByFoodExpencive(WineSelByFoodDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineFoodVo2> wine = MAPPER.selWineByFoodExpencive(dto);
        for (WineFoodVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;

    }

    public List<WineFoodVo2> selWineByFoodCheap(WineSelByFoodDto2 dto) {
        dto.setStartIdx((dto.getPage() - 1) * dto.getRow());

        List<WineFoodVo2> wine = MAPPER.selWineByFoodCheap(dto);
        for (WineFoodVo2 vo : wine) {
             if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }
}
