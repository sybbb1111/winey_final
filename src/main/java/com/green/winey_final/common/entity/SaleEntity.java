package com.green.winey_final.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_sale")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
public class SaleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long saleId;

    @ManyToOne
    @JoinColumn(name = "productId", updatable = false, nullable = false)
    private ProductEntity productEntity;

    @Column(length = 11)
    private Long sale;

    @Column(length = 11)
    private Long salePrice;

    @Column(length = 11)
    private Long saleYn;

    private String startSale; //이거 두개 확인 필요 //LocalDateTime을 String으로 바꿈
    private String endSale;

}
