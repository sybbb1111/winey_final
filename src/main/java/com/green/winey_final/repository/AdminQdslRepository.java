package com.green.winey_final.repository;

import com.green.winey_final.admin.model.*;
import com.green.winey_final.repository.support.PageCustom;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminQdslRepository {
    PageCustom<ProductVo> selProductAll(Pageable pageable, String str);
    PageCustom<UserVo> selUserAll(Pageable pageable, String searchType, String str);

    //가입 회원별 상세 주문 내역
    PageCustom<UserOrderDetailVo> selUserOrderByUserId(Long userId, Pageable pageable);
    UserInfo selUserInfoByUserId(Long userId, Pageable pageable);
    //주문 내역
    PageCustom<OrderListVo> selOrderAll(Pageable pageable);
    QueryResults<OrderListVo> selOrderAll2(Pageable pageable);
    //매장 리스트
    PageCustom<StoreVo> selStoreAll(Pageable pageable, String searchType, String str);

    //상세 주문 내역
    List<OrderDetail1> selOrderDetailByOrderId(int orderId, Pageable pageable);
    OrderDetail2 selOrderDetailByOrderId2(int orderId, Pageable pageable);

    //상품수정용 상품디테일
    AdminProductDetailVo selPutProductInfo1(int productId);
    List<Long> selPutProductInfo2(int productId);
    List<Long> selPutProductInfo3(int productId);

    //환불 내역
    PageCustom<OrderRefundVo> selOrderRefund(Pageable pageable);

    //할인률 등록 상품 리스트
    PageCustom<ProductSaleVo> selProductSaleAll(Pageable pageable);
}
