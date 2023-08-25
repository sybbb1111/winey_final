package com.green.winey_final.repository;


import com.green.winey_final.common.entity.AromaEntity;
import com.green.winey_final.common.entity.CategoryEntity;
import com.green.winey_final.common.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AromaRepository extends JpaRepository<AromaEntity, Long> {


}
