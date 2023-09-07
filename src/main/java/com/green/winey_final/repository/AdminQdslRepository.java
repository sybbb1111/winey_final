package com.green.winey_final.repository;

import com.green.winey_final.admin.model.*;
import com.green.winey_final.repository.support.PageCustom;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminQdslRepository {
    PageCustom<ProductVo> selProductAll(Pageable pageable, String str);
    PageCustom<UserVo> selUserAll(Pageable pageable, String searchType, String str);

    //가입 회원별 상세 주문 내역
    PageCustom<UserOrderDetailVo> selUserOrderByUserId(Long userId, Pageable pageable);
    UserInfo selUserInfoByUserId(Long userId, Pageable pageable);

    PageCustom<OrderListVo> selOrderAll(Pageable pageable);
    //매장 리스트
    PageCustom<StoreVo> selStoreAll(Pageable pageable, String searchType, String str);

    //상세 주문 내역
    List<OrderDetail1> selOrderDetailByOrderId(int orderId, Pageable pageable);
    OrderDetail2 selOrderDetailByOrderId2(int orderId, Pageable pageable);
}
