package com.green.winey_final.order2;

import com.green.winey_final.order2.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper2 {
    List<OrderEntity2> selOrder(UserIdDto dto);
    int cancelOrder(OrderCancelDto dto);
    int pickupFinishOrder(OrderPickupFinishDto dto);
    List<OrderDetailVo12> selOrderDetail1(UserIdDto dto);
    OrderDetailVo22 selOrderDetail2(UserIdDto dto);




}
