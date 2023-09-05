package com.green.winey_final.payment;

import com.green.winey_final.payment.model.PaymentInsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Tag(name = "결제")
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/cartpayment") //장바구니에 담겨있는 제품 결제
    @Operation(summary = "결제", description =
            "response data : 오더 번호 <br>"
                    + "storeId: 매장PK값, <br>"
                    + "pickupTime: 픽업타임 <br>")
    public long postPayment(@RequestBody PaymentInsDto dto) {
        return service.insPayment(dto);
    }

}

