package com.green.winey_final.common.entity;


import com.green.winey_final.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "t_order_detail")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
public class OrderDetailEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long orderDetailId;

    @ManyToOne
    @JoinColumn(name = "orderId", updatable = false, nullable = false)
    private OrderEntity orderEntity;

    @ManyToOne
    @JoinColumn(name = "productId", updatable = false, nullable = false)
    private ProductEntity productEntity;

    @Column(length = 11)
    private Long quantity;

    @Column(length = 11)
    private Long salePrice;
}
