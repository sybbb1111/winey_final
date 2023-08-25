package com.green.winey_final.main;


import com.green.winey_final.common.entity.CategoryEntity;
import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.repository.MainRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/main")
public class MainController {

    private final MainService SERVICE;
    private final MainRepository MAIN_REP;


    @GetMapping
    public ResponseEntity<List<ProductEntity>> getWines(@RequestParam(defaultValue = "1") int page
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

//    @GetMapping("/search")
//    @Operation(summary = "아이템 검색 및 검색리스트"
//            , description = "" +
//            "\"text\": [-] 검색어,<br>" +
//            "\"page\": [-] 리스트 페이지,<br>" +
//            "\"row\": [고정] 아이템 개수,<br>" +
//            "\"cate\": [-] 카테고리(11: 축산물, 16: 수산물, 13: 소스/드레싱, 18: 밀키트, 17: 농산물),<br>" +
//            "\"sort\": [1] 판매순 랭킹(0 : 최신순, 1: 오래된순, 2: 높은가격순, 3: 낮은가격순)  <br>"
//    )
//    public ItemSelDetailRes getSearchItem(@RequestParam(value = "cate",required=false)Long cate,
//                                          @RequestParam(value = "text",required=false)String text,
//                                          @RequestParam(defaultValue = "1")int page,
//                                          @RequestParam(defaultValue = "15")int row,
//                                          @RequestParam(defaultValue = "0")int sort) {
//
//        // 로그인을 안한 비회원인 경우 아이템 리스트 뿌려줌
//        ItemSearchDto dto = new ItemSearchDto();
//        dto.setText(text);
//        dto.setPage(page);
//        dto.setRow(row);
//        dto.setIitemCategory(cate);
//        dto.setSort(sort);
//
//        return SERVICE.searchItem(dto);
//    }
}
