package com.green.winey_final.common.entity;


import com.green.winey_final.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Entity
@Table(name = "t_product")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long productId;

    @Column(length = 100, nullable = false)
    private String nmKor;

    @Column(length = 100, nullable = false)
    private String nmEng;

    @Column(length = 20, nullable = false)
    private int price;

    private String pic;

    @Column(length = 3)
    private int promotion;

    @Column(length = 3)
    private int beginner;

    @Column(length = 11)
    private int alcohol;

    @Column(length = 11)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "categoryId", updatable = false, nullable = false)
    private CategoryEntity categoryEntity;

    @ManyToOne
    @JoinColumn(name = "featureId", updatable = false, nullable = false)
    private FeatureEntity featureEntity;

    @ManyToOne
    @JoinColumn(name = "countryId", updatable = false, nullable = false)
    private CountryEntity countryEntity;

}
