package com.green.winey_final.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "t_category")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@DynamicInsert
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long categoryId;

    @Column(nullable = false, length = 20)
    private String nm;

    //temp컬럼은 사용하지 않기 때문에 작성x
}
