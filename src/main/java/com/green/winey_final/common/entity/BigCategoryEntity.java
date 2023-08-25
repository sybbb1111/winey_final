package com.green.winey_final.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "t_big_category")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class BigCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long bigCategoryId;

    @Column(nullable = false, length = 30)
    @Size(min = 2, max = 20)
    private String bKind;
}
