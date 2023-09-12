package com.green.winey_final.repository;

import com.green.winey_final.common.entity.ProductEntity;
import com.green.winey_final.common.entity.WinePairingEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WinePairingRepository extends JpaRepository<WinePairingEntity,Long> {
    @Transactional
    int deleteByProductEntity(ProductEntity productEntity);
//    List<WinePairingEntity> findWinePairingEntityByProductEntity_ProductIdDistinct(Long productId);
}
