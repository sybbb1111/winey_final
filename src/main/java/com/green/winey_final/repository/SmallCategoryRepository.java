package com.green.winey_final.repository;


import com.green.winey_final.common.entity.AromaEntity;
import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.common.entity.SmallCategoryEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmallCategoryRepository extends JpaRepository<SmallCategoryEntity, Long> {


    /** 음식별 와인..JPA실패 */
    List<SmallCategoryEntity> findTop2ByBigCategoryEntity_BigCategoryId(Long bigCate);
    //top갯수By하면 limit처럼 사용가능 갯수 안넣고 그냥 topBy하면 디폴트로 1개 나옴

}
