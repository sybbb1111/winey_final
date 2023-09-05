package com.green.winey_final.order;

import com.green.winey_final.order.model.OrderEntity2;
import com.green.winey_final.order.model.DetailVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "주문내역 페이지")
@RestController
@RequestMapping("/api/orderList")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping("/user")
    @Operation(summary = "유저별 주문내역 리스트", description =
            "")
    public List<OrderEntity2> selOrder(){
        return service.selOrder();
    }

    @PutMapping("/cancel")
    @Operation(summary = "주문 취소", description = "주문상태를 주문 취소로 업데이트")
    public int cancelOrder(@RequestParam Long orderId) {
        return service.cancelOrder(orderId);
    }

    @PutMapping("/pickupFinish")
    @Operation(summary = "픽업 완료", description = "주문상태를 픽업완료로 업데이트")
    public int pickupFinishOrder(@RequestParam Long orderId){
        return service.pickupFinishOrder(orderId);
    }


    @GetMapping("/detail")
    @Operation(summary = "주문 상세 내역", description = "원래 주문내역이 있으면 주문상세내역도 있어야하는데, 더미데이터를 임의로 " +
            "넣다보니 vo1은 없고 vo2만 있는 경우가 있어요 이거는 오류는 아닙니다 ㅠㅠ 예)orderId 3번")
    public DetailVo selOrderDetail(@RequestParam Long orderId) {
        return service.selOrderDetail(orderId);
    }



}
