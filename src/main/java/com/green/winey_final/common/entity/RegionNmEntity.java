package com.green.winey_final.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "t_region_nm")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@DynamicInsert
public class RegionNmEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long regionNmId;

    @Column(nullable = false, length = 15)
    private String regionNm;


}
