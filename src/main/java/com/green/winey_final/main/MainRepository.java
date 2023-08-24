package com.green.winey_final.main;


import com.green.winey_final.common.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value = "SELECT a.product_id, a.category_id, a.feature_id, a.country_id," +
            " c.aroma_category_id as aromaId, a.nm_kor, a.nm_eng, a.price, a.quantity," +
            " a.pic, a.promotion, a.beginner, a.alcohol, b.sale, b.sale_price, b.sale_yn " +
            "FROM t_product a " +
            "INNER JOIN t_sale b ON a.product_id = b.product_id " +
            "INNER JOIN t_aroma c ON a.product_id = c.product_id " +
            "WHERE a.category_id = 1", nativeQuery = true)
    List<ProductEntity> findAllByRedWine(@Param("categoryId") Long catecoryId);

}
