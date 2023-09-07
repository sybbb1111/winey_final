package com.green.winey_final.repository;

import com.green.winey_final.admin.model.ProductVo;
import com.green.winey_final.admin.model.UserInfo;
import com.green.winey_final.admin.model.UserOrderDetailVo;
import com.green.winey_final.admin.model.UserVo;
import com.green.winey_final.repository.support.PageCustom;
import org.springframework.data.domain.Pageable;

public interface AdminQdslRepository {
    PageCustom<ProductVo> selProductAll(Pageable pageable, String str);
    PageCustom<UserVo> selUserAll(Pageable pageable, String searchType, String str);

    //가입 회원별 상세 주문 내역
    PageCustom<UserOrderDetailVo> selUserOrderByUserId(Long userId, Pageable pageable);
    UserInfo selUserInfoByUserId(Long userId, Pageable pageable);
}
