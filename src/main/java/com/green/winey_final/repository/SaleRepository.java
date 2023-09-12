package com.green.winey_final.repository;


import com.green.winey_final.common.entity.AromaEntity;
import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.common.entity.SaleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {
    @Transactional
    int deleteByProductEntity(ProductEntity productEntity);

    List<SaleEntity> findSaleEntityByStartSale(String yearMonth);
    SaleEntity findByProductEntity(ProductEntity productEntity);
}
