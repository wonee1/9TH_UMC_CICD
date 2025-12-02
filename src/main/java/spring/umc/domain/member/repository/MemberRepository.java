package spring.umc.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.umc.domain.member.dto.MyPageResponse;
import spring.umc.domain.member.entity.User;

public interface MemberRepository extends JpaRepository<User, Long> {

    /**
     * 마이페이지 — 닉네임, 이메일, 포인트, 리뷰 수 조회
     *
     * - 리뷰 수: Review 테이블에서 user.id 기준으로 COUNT
     */
    @Query("""
        SELECT new spring.umc.domain.member.dto.MyPageResponse(
            u.nickname,
            u.email,
            u.point,
            COUNT(DISTINCT r)
        )
        FROM User u
        LEFT JOIN spring.umc.domain.review.entity.Review r ON r.user.id = u.id
        WHERE u.id = :userId
        GROUP BY u.nickname, u.email, u.point
    """)
    MyPageResponse findMyPageByUserId(@Param("userId") Long userId);
}
