package com.green.winey_final.common.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "t_user_small_category")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString
@DynamicInsert
@EqualsAndHashCode
public class UserSmallCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long userSmallCategoryId;

    @ManyToOne
    @JoinColumn(name = "userId",updatable = false, nullable = false)
    private UserEntity userEntity;

    @Column(nullable = true)
    private Long smallCategoryId;

}
