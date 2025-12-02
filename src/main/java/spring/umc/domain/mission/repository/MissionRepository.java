package spring.umc.domain.mission.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.umc.domain.mission.entity.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    /**
     * 홈 화면 — 선택된 지역 내 미션 목록 (페이징)
     */
    @Query("""
        SELECT m
        FROM Mission m
        JOIN FETCH m.store s
        WHERE s.address LIKE CONCAT('%', :region, '%')
        ORDER BY m.createdAt DESC
    """)
    Page<Mission> findByRegion(@Param("region") String region, Pageable pageable);
}
