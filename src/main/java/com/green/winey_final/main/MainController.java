package com.green.winey_final.main;


import com.green.winey_final.common.entity.CategoryEntity;
import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.common.entity.SmallCategoryEntity;
import com.green.winey_final.common.entity.WinePairingEntity;
import com.green.winey_final.main.model.*;
import com.green.winey_final.search.model.WineSearchDto;
import com.green.winey_final.search.model.WineSelDetailRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "메인/연습장입니다!(◍•ᴗ•◍)♡ ✧*。")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {

    private final MainService SERVICE;

//    @GetMapping("/foodPairings")
//    @Operation(summary = "페어링별 와인")
//    public List<WinePairingEntity> getPairingWine(@RequestParam Long productId) {
//        return SERVICE.getPairing(productId);
//    }

    @GetMapping("/foodWines")
    @Operation(summary = "음식별 와인")
    public List<SmallCategoryEntity> getfoodWine(@RequestParam Long bigCate) {
        return SERVICE.getFood(bigCate);
    }

    @GetMapping("/countryWines")
    @Operation(summary = "나라별 와인")
    public List<ProductEntity> getCountryWine(@RequestParam Long countryId) {
        return SERVICE.getCountry(countryId);
    }

    /** 카테고리별 와인 리스트 (카테고리별, 국가별, 나라별, 금액대별, 최신등록순 어쩌고 저쩌고) */
    @GetMapping("/categoryWine")
    @Operation(summary = "항목별 와인 리스트"
            , description = "" +
            "\"text\": [-] 검색어,<br>" +
            "\"page\": [-] 리스트 페이지,<br>" +
            "\"row\": [고정] 아이템 개수,<br>" +
            "\"cate\": [-] 카테고리(1: 레드, 2: 화이트 3: 스파클링 4: 기타),<br>" +
            "\"bigCate\": [-] 카테고리(1: meet, 2: seafood, 3: otherfood),<br>" +
            "\"country\": [-] 카테고리(1: 미국, 3: 프랑스, 4: 이탈리아, 6: 칠레 /그외 기타),<br>" +
            "\"sort\": [1] 판매순 랭킹(0 : 최신순, 1: 높은가격순, 2: 낮은가격순)  <br>" +
            "\"price\": [1] 금액별 와인(0 : 2만원 이하, 1: 2~5만원 , 2: 5~10만원, 3: 10만원 이상)  <br>"
    )
    public WineCategoryDetailRes getSearchItem(@RequestParam(value = "cate",required=false)Long cate,
                                          @RequestParam(value = "bigCate",required=false)Long bigCate,
                                          @RequestParam(value = "country",required=false)Long country,
                                          @RequestParam(defaultValue = "1")int page,
                                          @RequestParam(defaultValue = "9")int row,
                                          @RequestParam(defaultValue = "0")int sort,
                                          @RequestParam(defaultValue = "0")int price) {

        WineCategoryDto dto = new WineCategoryDto();
        dto.setPage(page);
        dto.setRow(row);
        dto.setCategoryId(cate);
        dto.setBigCategoryId(bigCate);
        dto.setCountryId(country);
        dto.setSort(sort);
        dto.setPrice(price);

        return SERVICE.categoryWine(dto);
    }

    /** 연습용 */
    @GetMapping("/categoryWines")
    @Operation(summary = "연습용")
    public List<ProductEntity> getProductsByCategoryId(@RequestParam Long categoryId) {
        return SERVICE.getProductsByCategoryId(categoryId);
    }

}
