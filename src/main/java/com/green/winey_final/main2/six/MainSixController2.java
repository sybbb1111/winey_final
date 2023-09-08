package com.green.winey_final.main2.six;


import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.main2.MainMapper2;
import com.green.winey_final.main2.model.*;
import com.green.winey_final.recommend2.RecommendMapper2;
import com.green.winey_final.recommend2.model.LoginUserDto2;
import com.green.winey_final.recommend2.model.SelRecommendDto2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@Tag(name = "와인 리스트 6개")
@RestController
@RequestMapping("/api/main/6")
@RequiredArgsConstructor
public class MainSixController2 {


    private final MainSixService2 SERVICE;
    private final MainSixMapper2 MAPPER;
    private final MainMapper2 mainMapper;
    private final AuthenticationFacade facade;

    private final RecommendMapper2 recommendMapper;

    /*@GetMapping("/price")
    @Operation(summary = "가격별 와인리스트")
    public List<WineVo> getWineByPrice(@RequestParam int num) {
        WineSelByPriceDto dto = new WineSelByPriceDto();
        dto.setNumber(num);
        return SERVICE.selWineByPrice(dto);
    }*/

    //6개 출력
    @GetMapping("/price2")
    @Operation(summary = "2만원 미만 와인리스트 6개")
    public List<WineTotalVo2> getWineByPrice2limit6() {
        return SERVICE.selWineByPrice2limit6();
    }

    @GetMapping("/price25")
    @Operation(summary = "2-5만원 와인리스트 6개")
    public List<WineTotalVo2> getWineByPrice25limit6() {
        return SERVICE.selWineByPrice25limit6();
    }

    @GetMapping("/price510")
    @Operation(summary = "5-10만원 와인리스트 6개")
    public List<WineTotalVo2> getWineByPrice510limit6() {
        return SERVICE.selWineByPrice510limit6();
    }

    @GetMapping("/price10")
    @Operation(summary = "10만원 이상 와인리스트 6개")
    public List<WineTotalVo2> getWineByPrice10limit6() {
        return SERVICE.selWineByPrice10limit6();
    }

    @GetMapping("/country")
    @Operation(summary = "국가별 와인리스트 6개")
    public List<WineTotalVo2> getWineByCountrylimit6(@RequestParam Long countryId) {
        WineSelByCountryDto2 dto = new WineSelByCountryDto2();
        dto.setCountryId(countryId);
        return SERVICE.selWineByCountrylimit6(dto);
    }

    @GetMapping("/food")
    @Operation(summary = "음식별 와인리스트 6개")
    public List<WineFoodVo2> getWineByFoodlimit6(@RequestParam Long bigCategoryId) {
        WineSelByFoodDto2 dto = new WineSelByFoodDto2();
        dto.setBigCategoryId(bigCategoryId);
        return SERVICE.selWineByFoodlimit6(dto);
    }

    @GetMapping("/random-wines")
    @Operation(summary = "입문용 와인리스트 6개")
    //@Scheduled(cron = "0 0 0 * * *") // 매일 자정 마다 실행
    public List<WineRecommendVo2> getRandomWines() {

        //로그인한 userId 불러오기
        Long userId = facade.getLoginUserPk();

        LoginUserDto2 dto = new LoginUserDto2();
        dto.setUserId(userId);

        //불러온 userId값 넣어주기
        SelRecommendDto2 selRecommendDto2 = new SelRecommendDto2();
        selRecommendDto2.setUserId(userId);

        List<Long> recommandWines = recommendMapper.selUserinfo(selRecommendDto2);
        List<Long> orderList = MAPPER.orderList(userId);

        List<Long> getProductId = new ArrayList<>();
        List<Long> getProductOrderId = new ArrayList<>();

        List<WineRecommendVo2> selectedWines = new ArrayList<>();

        // userId 당 해당하는 productId 담기
        for (Long wineId : recommandWines) {
            getProductId.add(wineId);
        }

        for (Long orderId : orderList) {
            getProductOrderId.add(orderId);
        }

        for (int i = 0; i < getProductId.size(); i++) {
            Long productId = getProductId.get(i);

            if (recommandWines.contains(productId) && orderList.contains(productId)) {
                getProductId.remove(i);
                i--;
            }
        }
        //랜덤 6개 출력하기
        List<WineRecommendVo2> allWines = MAPPER.selWineByday(userId);
        int winesToDisplay = 6;

        if (getProductId.size() <= winesToDisplay) {
            selectedWines.addAll(allWines);
        } else {
            Set<Integer> selectedIndexes = new HashSet<>();
            Random random = new Random();

            while (selectedIndexes.size() < winesToDisplay) {
                int randomIndex = random.nextInt(getProductId.size());
                selectedIndexes.add(randomIndex);
            }

            for (int index : selectedIndexes) {
                selectedWines.add(allWines.get(index));
            }
        }

        return selectedWines;
    }
}
