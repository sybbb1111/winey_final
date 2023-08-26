package com.green.winey_final.main;


import com.green.winey_final.common.entity.CategoryEntity;
import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.main.model.*;
import com.green.winey_final.repository.MainRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {

    private final MainService SERVICE;
    private final MainRepository MAIN_REP;


    @GetMapping
    public ResponseEntity<List<ProductEntity>> getWines1(@RequestParam(defaultValue = "1") int page
                                                        , @RequestParam(defaultValue = "9") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ProductEntity> products = MAIN_REP.findAll(pageable);

        return ResponseEntity.ok(products.getContent());
    }

    @GetMapping("/products")
    public List<ProductEntity> getProductsByCategoryId() {
        Long categoryId = 1L;
        return SERVICE.getProductsByCategoryId(categoryId);
    }

    @GetMapping("/search")
    @Operation(summary = "아이템 검색 및 검색리스트"
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
    public WineSelDetailRes getSearchItem(@RequestParam(value = "cate",required=false)Long cate,
                                          @RequestParam(value = "bigCate",required=false)Long bigCate,
                                          @RequestParam(value = "country",required=false)Long country,
                                          @RequestParam(value = "text",required=false)String text,
                                          @RequestParam(defaultValue = "1")int page,
                                          @RequestParam(defaultValue = "9")int row,
                                          @RequestParam(defaultValue = "0")int sort,
                                          @RequestParam(defaultValue = "0")int price) {

        WineSearchDto dto = new WineSearchDto();
        dto.setText(text);
        dto.setPage(page);
        dto.setRow(row);
        dto.setCategoryId(cate);
        dto.setBigCategoryId(bigCate);
        dto.setCountryId(country);
        dto.setSort(sort);
        dto.setPrice(price);

        return SERVICE.searchWine(dto);
    }


}
