package com.green.winey_final.repository;


import com.green.winey_final.common.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductKorRepository extends JpaRepository<ProductEntity,Long> {
}
