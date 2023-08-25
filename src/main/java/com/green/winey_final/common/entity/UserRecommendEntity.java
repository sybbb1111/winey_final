package com.green.winey_final.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;


@Entity
@Data
@Table(name = "t_user_recommend")
@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@DynamicInsert
public class UserRecommendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long userRecommendId;
    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, nullable = false)
    private UserEntity userEntity;

    @Column(nullable = true,length = 20)
    private Long categoryId;
    @Column(nullable = true,length = 20)
    private Long countryId;
    @Column(nullable = true,length = 20)
    private Long smallCateGoryId;
    @Column(nullable = false,length = 20,columnDefinition = "bigint default 0")
    private Long priceRange;
    @Column(nullable = true,length = 20)
    private Long AromaCategoryId;
}
