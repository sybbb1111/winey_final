package com.green.winey_final.repository;


import com.green.winey_final.common.entity.AromaEntity;
import com.green.winey_final.common.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {


}
