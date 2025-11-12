package spring.umc.domain.review.repository;

import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.umc.domain.review.dto.ReviewResponseDto;

/**
 * Review QueryDSL 커스텀 쿼리용 인터페이스
 */
public interface ReviewQueryDsl {
    /**
     * BooleanBuilder 기반 리뷰 조회
     *
     * @param builder  동적 조건 (userId, storeName, rating 등)
     * @param pageable 페이징 정보
     * @return Page 형태의 ReviewResponseDto 결과
     */
    Page<ReviewResponseDto> findMyReviewsByBuilder(BooleanBuilder builder, Pageable pageable);
}
