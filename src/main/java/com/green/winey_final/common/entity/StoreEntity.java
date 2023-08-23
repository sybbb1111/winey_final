package com.green.winey_final.common.entity;

import com.green.jpaentitytest.config.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "t_feed_cmt")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StoreEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long storeId;

    @ManyToOne
    @JoinColumn(name = "regionNmId", updatable = false, nullable = false)
    private RegionNmEntity regionNmEntity;

    @Column(nullable = false, length = 25)
    @Size(min = 2, max = 20)
    private String nm;

    @Column(nullable = false, length = 100)
    @Size(min = 2, max = 100)
    private String address;

    @Column(length = 11)
    private String tel;
}
