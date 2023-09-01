package com.green.winey_final.common.entity;


import com.green.winey_final.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "t_cart")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@DynamicInsert
@DynamicUpdate
public class CartEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long cartId;

    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, nullable = false)
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "productId", updatable = false, nullable = false)
    private ProductEntity productEntity;

    @Column(length = 11)
    private int quantity;

}
