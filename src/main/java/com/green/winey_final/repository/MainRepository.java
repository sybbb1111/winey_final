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

    List<ProductEntity> findAll();
    List<ProductEntity> findAllByRedWine(Long categoryId);
    List<ProductEntity> findAllByCategoryId(Long categoryId, Pageable pageable);

}
