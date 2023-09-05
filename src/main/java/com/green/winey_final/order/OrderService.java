package com.green.winey_final.order;

import com.green.winey_final.order.model.OrderEntity2;
import com.green.winey_final.common.config.security.AuthenticationFacade;
import com.green.winey_final.order.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper mapper;
    private final AuthenticationFacade facade;


    List<OrderEntity2> selOrder(){
        UserIdDto dto =new UserIdDto();
        dto.setUserId(facade.getLoginUserPk());
        List<OrderEntity2> list = mapper.selOrder(dto);

        for(OrderEntity2 entity : list){
            entity.setOrderDate(entity.getOrderDate());
            entity.setUserId(facade.getLoginUserPk());
            entity.setOrderId(entity.getOrderId());
            entity.setPayment(entity.getPayment());
            entity.setTotalOrderPrice(entity.getTotalOrderPrice());
            entity.setStoreNm("이마트 " + entity.getStoreNm());
            entity.setOrderStatus(entity.getOrderStatus());

            try {
                String strDate = entity.getPickupTime();
                SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat newDtFormat = new SimpleDateFormat("MM월 dd일 E요일 HH:mm", Locale.KOREA);
                // String 타입을 Date 타입으로 변환

                Date formatDate = dtFormat.parse(strDate);
                // Date타입의 변수를 새롭게 지정한 포맷으로 변환

                String strNewDtFormat = newDtFormat.format(formatDate);

                entity.setPickupTime(strNewDtFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(entity.getCount() >= 2 ){
                entity.setNmKor(entity.getNmKor() + " 외 " + (entity.getCount() - 1) + "건");
            } else if (entity.getCount() == 1) {
                entity.setNmKor(entity.getNmKor());
            }
        }
        return list;
    }


    int cancelOrder(Long orderId) {
        OrderCancelDto dto = new OrderCancelDto();
        dto.setUserId(facade.getLoginUserPk());
        dto.setOrderId(orderId);
        return mapper.cancelOrder(dto);
    }

    int pickupFinishOrder(Long orderId) {
        OrderPickupFinishDto dto = new OrderPickupFinishDto();
        dto.setOrderId(orderId);
        dto.setUserId(facade.getLoginUserPk());
        return mapper.pickupFinishOrder(dto);
    }





    public DetailVo selOrderDetail(Long orderId){
        UserIdDto dto = new UserIdDto();
        dto.setOrderId(orderId);
        dto.setUserId(facade.getLoginUserPk());

        List<OrderDetailVo1> vo1 = mapper.selOrderDetail1(dto);
        for(OrderDetailVo1 detailVo1 : vo1) {
            if(detailVo1.getReviewYn() >= 1 ){
                detailVo1.setReviewYn(1);
            }

        }


        OrderDetailVo2 vo2 = mapper.selOrderDetail2(dto);
        if(vo2 != null) {
            try {
                String strDate = vo2.getPickupTime();
                SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat newDtFormat = new SimpleDateFormat("MM월 dd일 E요일 HH:mm", Locale.KOREA);
                // String 타입을 Date 타입으로 변환

                Date formatDate = dtFormat.parse(strDate);
                // Date타입의 변수를 새롭게 지정한 포맷으로 변환

                String strNewDtFormat = newDtFormat.format(formatDate);

                vo2.setPickupTime(strNewDtFormat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vo2.setStoreNm("이마트 " + vo2.getStoreNm());
        } else {
            return null;
        }


        return DetailVo.builder()
                .vo1(vo1)
                .vo2(vo2)
                .build();

    }




}




