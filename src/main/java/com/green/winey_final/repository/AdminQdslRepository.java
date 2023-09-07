package com.green.winey_final.repository;

import com.green.winey_final.admin.model.ProductVo;
import com.green.winey_final.repository.support.PageCustom;
import org.springframework.data.domain.Pageable;

public interface AdminQdslRepository {
    PageCustom<ProductVo> selProductAll(Pageable pageable, String str);
}
