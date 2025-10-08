package spring.umc.domain.mission.entity;

import jakarta.persistence.*;
import lombok.*;
import spring.umc.domain.store.entity.Store;
import spring.umc.global.entity.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Integer rewardPoint = 500;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}