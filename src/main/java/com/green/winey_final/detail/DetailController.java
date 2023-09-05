package com.green.winey_final.detail;

import com.green.winey_final.detail.model.SelWineKorNm;
import com.green.winey_final.detail.model.WineDetailVo;
import com.green.winey_final.detail.model.WineVo3;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "와인상세페이지")
@RestController
@RequestMapping("/api/detail")
@RequiredArgsConstructor
public class DetailController {
    private final DetailService SERVICE;

    @GetMapping("/{productId}")
    @Operation(summary = "와인 디테일 페이지", description =
                "temp -> 스파클링일 때 10, 화이트일 때 15, 레드/기타일 때 18로 표시되도록 했음<br>" +
                 "promotion -> 추천상품에 해당할 때 1, 아닐 때 0<br>" +
                 "beginner -> 입문자 추천상품일 때 1, 아닐 때 0<br>" +
                 "selPairing -> 페어링 음식 목록<br>" +
                 "selSale -> 세일항목일 경우 숫자로 % 표시, 세일아닐 시 null 표시<br>" +
                 "level -> 입문 난이도 표시<br>" +
                 "selReview -> 리뷰개수 표시. 좋아요, 보통이에요, 취향이 아니에요 순서로 표시(예 - 2,1,0이면 좋아요 2개, 보통이에요 1개, 취향이 아니에요 0개)")
    public WineVo3 getWineDetail(@PathVariable Long productId){
        WineDetailVo vo = new WineDetailVo();
        vo.setProductId(productId);
        return SERVICE.selWineDetail(productId);
    }

    @GetMapping("/korNm/{productId}")
    @Operation(summary = "와인 한글이름")
    public SelWineKorNm getWineKorNm(@PathVariable Long productId) {
        return SERVICE.selKorNm(productId);
    }
}
