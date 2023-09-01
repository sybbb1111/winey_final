package com.green.winey_final.repository;

import com.green.winey_final.common.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import scala.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    List<ProductEntity> findByCategoryEntityCategoryId(Long categoryId, Pageable pageable);

    List<ProductEntity> findByCountryEntityCountryId(Long countryId, Pageable pageable);

    @Query("select a from ProductEntity a join fetch a.categoryEntity " +
            "join fetch a.featureEntity join fetch a.countryEntity")
    List<ProductEntity> selProductList();

}
