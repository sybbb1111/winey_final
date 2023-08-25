package com.green.winey_final.repository;


import com.green.winey_final.common.entity.AromaEntity;
import com.green.winey_final.common.entity.SaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends JpaRepository<SaleEntity, Long> {}
