package com.green.winey_final.common.entity;


import com.green.winey_final.common.config.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "t_user_token")
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@IdClass(UserId.class)
public class UserTokenEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long ip;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "userId", updatable = false, nullable = false)
    private UserEntity userEntity;

    @Column(length = 200)
    private String accessToken;

    @Column(length = 200)
    private String refreshToken;
}
