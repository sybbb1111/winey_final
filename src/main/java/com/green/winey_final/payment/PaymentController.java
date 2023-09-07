package com.green.winey_final.payment;

import com.green.winey_final.payment.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "결제")
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/cartpayment") //장바구니에 담겨있는 제품 결제
    @Operation(summary = "카트 결제", description =
            "<b>response data</b> : 오더 번호 <br><br>"
                    + "storeId: 매장PK값, <br>"
                    + "pickupTime: 픽업타임 <br>")
    public long postPayment(@RequestBody PaymentInsDto dto) {
        return service.insPayment(dto);
    }

    @PostMapping("/eachpayment") //와인상세페이지에서 바로 구매하기버튼 눌러서 결제
    @Operation(summary = "개별 결제", description =
                    "<b>response data</b> : 오더 번호 <br><br>"
                    + "productId: 상품 pk, <br>"
                    + "storeId: 지점 pk, <br>"
                    + "pickupTime: 픽업시간 <br>"
                    + "quantity: 수량 <br>")
    public long postEachPayment(@RequestBody EachPaymentInsDto dto) {
        return service.insEachPayment(dto);
    }

    @PutMapping("/orderstatus") //주문상태 변경
    @Operation(summary = "주문상태 변경", description =
            "<b>response data</b> : 1 <br><br>"
            + "orderId : 오더 번호 <br>"
            + "orderStatus: 주문 상태, <br>")
    public int putPayment(@RequestBody PaymentUpdDto dto) {
        return service.updPayment(dto);
    }

    @PostMapping("/userreview") //리뷰 등록
    @Operation(summary = "리뷰 작성", description =
                    "<b>response data</b> : review 번호 <br><br>"
                    + "orderDetailId: 오더 번호 <br>"
                    + "reviewLevel: (1: 좋아요, 2:보통, 3:별로)")
    public long postReview(@RequestBody ReviewInsDto dto) {
        return service.insReview(dto);
    }


    @GetMapping("/pickregion") //픽업지역 출력
    @Operation(summary = "픽업 지역", description =
            "userId: 사용자 pk값, <br>"
                    + "regionNmId: 지역 pk값 <br>"
                    + "regionNm: 지역 이름 <br>"
                    + "storeId: 가게 pk값 <br>"
                    + "nm: 지점명 <br>"
                    + "address: 주소")
    public List<RegionSelVo> getRegion() {
        return service.selRegion();
    }


}

