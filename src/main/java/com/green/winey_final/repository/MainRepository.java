package com.green.winey_final.repository;


import com.green.winey_final.common.entity.CategoryEntity;
import com.green.winey_final.common.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainRepository extends JpaRepository<ProductEntity, Long> {

    List<ProductEntity> findByCategoryEntityCategoryId(Long categoryId);

    @Query("select a from ProductEntity a join fetch a.categoryEntity " +
            "join fetch a.featureEntity join fetch a.countryEntity")
    List<ProductEntity> selProductList();


}
