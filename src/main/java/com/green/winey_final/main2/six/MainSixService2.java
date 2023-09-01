package com.green.winey_final.main2.six;

import com.green.winey_final.main2.model.WineFoodVo2;
import com.green.winey_final.main2.model.WineSelByCountryDto2;
import com.green.winey_final.main2.model.WineSelByFoodDto2;
import com.green.winey_final.main2.model.WineTotalVo2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MainSixService2 {

    private final MainSixMapper2 MAPPER;

    //6개씩 출력
    public List<WineTotalVo2> selWineByPrice2limit6() {
        List<WineTotalVo2> wine = MAPPER.selWineByPrice2limit6();
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }

    public List<WineTotalVo2> selWineByPrice25limit6() {
        List<WineTotalVo2> wine = MAPPER.selWineByPrice25limit6();
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }

    public List<WineTotalVo2> selWineByPrice510limit6() {
        List<WineTotalVo2> wine = MAPPER.selWineByPrice510limit6();
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }

    public List<WineTotalVo2> selWineByPrice10limit6() {
        List<WineTotalVo2> wine = MAPPER.selWineByPrice10limit6();
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }

    public List<WineTotalVo2> selWineByCountrylimit6(WineSelByCountryDto2 dto) {
        List<WineTotalVo2> wine = MAPPER.selWineByCountrylimit6(dto);
        for (WineTotalVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }

    public List<WineFoodVo2> selWineByFoodlimit6(WineSelByFoodDto2 dto) {
        List<WineFoodVo2> wine = MAPPER.selWineByFoodlimit6(dto);
        for (WineFoodVo2 vo : wine) {
            if(vo.getSaleYn() == 0){
                vo.setSale(0);
                vo.setSalePrice(vo.getPrice());
            }
        }
        return wine;
    }

//    public List<WineTotalVo> selWineByday() {
//        return MAPPER.selWineByday();
//    }

   /* @Scheduled(cron = "0 0 0 * * *") // 매일 자정마다 뿌려
    public void selWineByday() {
        List<WineVo> list = MAPPER.selWineByday(); // beginner=1 와인 정보 가져오기

        int totalWines = list.size(); // 가져온 와인 정보의 총 개수
        int wineToDisplay = 6; // 하루에 뿌릴 와인 개수

        if (totalWines <= wineToDisplay) {
            log.info("하루에 표기할 와인: {}", list);
        } else {
            List<WineVo> selectedWine = new ArrayList<>();

            // 랜덤한 인덱스로 랜덤값 선택
            Set<Integer> selectedIndexes = new HashSet<>();
            Random random = new Random();

            while (selectedIndexes.size() < wineToDisplay) {
                int randomIndex = random.nextInt(totalWines);
                selectedIndexes.add(randomIndex);
            }

            for (int index : selectedIndexes) {
                selectedWine.add(list.get(index));
            }

            log.info("Randomly selected wines: {}", selectedWine);
        }
    }*/
}
