package spring.umc.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import spring.umc.domain.member.dto.MyPageResponse;
import spring.umc.domain.member.entity.User;

public interface MemberRepository extends JpaRepository<User, Long> {

    /**
     * 마이페이지 — 닉네임, 이메일, 포인트, 리뷰/문의 수 조회
     *
     * - 리뷰 수: reviews 테이블에서 user_id 기준으로 COUNT
     * - 문의 수: inquiries 테이블에서 user_id 기준으로 COUNT
     * - User의 포인트는 User 엔티티의 point 컬럼 사용
     */
    @Query("""
        SELECT new spring.umc.domain.member.dto.MyPageResponse(
            u.nickname,
            u.email,
            u.point,
            (SELECT COUNT(r) FROM Review r WHERE r.user.id = u.id),
            (SELECT COUNT(i) FROM Inquiry i WHERE i.user.id = u.id)
        )
        FROM User u
        WHERE u.id = :userId
    """)
    MyPageResponse findMyPageByUserId(@Param("userId") Long userId);
}
