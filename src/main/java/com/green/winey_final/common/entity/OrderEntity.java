package com.green.winey_final.common.entity;


import com.green.winey_final.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_order")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "storeId", updatable = false, nullable = false)
    private StoreEntity storeEntity;

    @Column(length = 20)
    private Long totalOrderPrice;

    private LocalDateTime orderTime; //이거 확인 필요

    @Column(length = 11)
    private Long payment;

    private LocalDateTime pickupTime; //이거 확인 필요

    @Column(length = 11)
    private Long orderStatus;

}
