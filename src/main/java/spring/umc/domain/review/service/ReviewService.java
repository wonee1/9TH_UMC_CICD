package spring.umc.domain.review.service;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.umc.domain.member.entity.User;
import spring.umc.domain.member.repository.MemberRepository;
import spring.umc.domain.review.dto.ReviewResponseDto;
import spring.umc.domain.review.entity.QReview;
import spring.umc.domain.store.entity.QStore;
import spring.umc.domain.review.repository.ReviewQueryDsl;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewQueryDsl reviewQueryDsl;
    private final MemberRepository memberRepository;

    /**
     * 내가 작성한 리뷰 조회
     *
     * - BooleanBuilder를 사용하여 동적 검색 조건을 조립
     * - Repository 계층에서는 builder를 이용해 실제 QueryDSL 쿼리 수행
     *
     * @param userId      현재 로그인한 사용자 ID
     * @param storeName   가게 이름으로 필터링 (부분 검색)
     * @param ratingGroup 별점 그룹
     * @param pageable    페이징 정보 (page, size, sort)
     * @return 조건에 맞는 리뷰 목록 Page<ReviewResponseDto>
     */
    public Page<ReviewResponseDto> getMyReviews(Long userId, String storeName, Integer ratingGroup, Pageable pageable) {
        // 유저 검증
        User user = memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다. userId=" + userId));

        // Q클래스를 정의한다 QueryDSL용 엔티티 메타데이터
        QReview review = QReview.review;
        QStore store = QStore.store;

        //BooleanBuilder를 통해 동적 where 조건 조립한다
        BooleanBuilder builder = new BooleanBuilder();

        // 로그인한 사용자의 리뷰만 조회하도록 한다
        builder.and(review.user.id.eq(userId));

        //가게명 필터링 — 부분 검색
        if (storeName != null && !storeName.isBlank()) {
            builder.and(store.name.containsIgnoreCase(storeName));
        }

        //별점 구간 필터링
        if (ratingGroup != null) {
            double lower = ratingGroup;
            double upper = ratingGroup + 1.0;
            builder.and(review.rating.between(lower, upper));
        }

        // Repository 실행한다 이때 builder 조건과 페이징 정보를 넘긴다
        return reviewQueryDsl.findMyReviewsByBuilder(builder, pageable);
    }
}
