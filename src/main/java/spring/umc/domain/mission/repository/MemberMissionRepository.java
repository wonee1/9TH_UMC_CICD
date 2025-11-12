package spring.umc.domain.mission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.umc.domain.mission.entity.UserMission;

import java.time.LocalDateTime;
import java.util.List;

public interface MemberMissionRepository extends JpaRepository<UserMission, Long> {

    /**
     * 특정 유저의 진행중/완료 미션 조회 (페이지네이션)
     */
    @Query("""
        SELECT um
        FROM UserMission um
        JOIN FETCH um.mission m
        JOIN FETCH m.store s
        WHERE um.user.id = :userId
          AND um.status IN ('in_progress', 'completed')
        ORDER BY um.assignedAt DESC
    """)
    Page<UserMission> findAllByUserIdAndStatusInOrderByAssignedAtDesc(
            @Param("userId") Long userId,
            Pageable pageable
    );

    /**
     * 특정 미션이 완료 상태인지 여부 확인 (리뷰 작성 가능 조건)
     */
    boolean existsByUserIdAndMissionIdAndStatus(Long userId, Long missionId, String status);

    /**
     * 커서 기반 페이징 (1순위 포인트, 2순위 최신 리뷰순)
     * Mission과 Review는 직접 매핑되어 있지 않으므로
     * Review 엔티티를 ON 절로 명시적으로 조인해야 한다.
     */
    @Query("""
        SELECT um
        FROM UserMission um
        JOIN FETCH um.mission m
        JOIN FETCH m.store s
        LEFT JOIN spring.umc.domain.review.entity.Review r ON r.mission.id = m.id
        WHERE um.user.id = :userId
          AND um.status IN ('in_progress', 'completed')
          AND (
                m.rewardPoint < :cursorRewardPoint
                OR (
                    m.rewardPoint = :cursorRewardPoint
                    AND r.createdAt < :cursorReviewAt
                )
          )
        GROUP BY um
        ORDER BY m.rewardPoint DESC, MAX(r.createdAt) DESC
    """)
    List<UserMission> findByCursorPagination(
            @Param("userId") Long userId,
            @Param("cursorRewardPoint") int cursorRewardPoint,
            @Param("cursorReviewAt") LocalDateTime cursorReviewAt
    );
}
