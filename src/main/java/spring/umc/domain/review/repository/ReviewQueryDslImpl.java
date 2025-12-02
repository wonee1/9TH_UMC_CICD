package spring.umc.domain.review.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import spring.umc.domain.review.dto.QReviewResponseDto;
import spring.umc.domain.review.dto.ReviewResponseDto;
import spring.umc.domain.review.entity.QReview;
import spring.umc.domain.store.entity.QStore;

import java.util.List;

/**
 * ReviewQueryDsl 구현체
 *
 * - BooleanBuilder 기반으로 동적 조건 조회
 * - QueryDSL JPAQueryFactory 사용
 * - DTO projection으로 성능 최적화
 */
@Repository
@RequiredArgsConstructor
public class ReviewQueryDslImpl implements ReviewQueryDsl {

    private final JPAQueryFactory queryFactory; // ✅ com.querydsl.jpa.impl.JPAQueryFactory

    @Override
    public Page<ReviewResponseDto> findMyReviewsByBuilder(BooleanBuilder builder, Pageable pageable) {
        QReview review = QReview.review;
        QStore store = QStore.store;

        // 메인 조회 쿼리
        List<ReviewResponseDto> results = queryFactory
                .select(new QReviewResponseDto(
                        review.id,
                        review.content,
                        review.rating,
                        store.name
                ))
                .from(review)
                .join(review.store, store)
                .where(builder)
                .orderBy(review.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 카운트
        Long total = queryFactory
                .select(review.count())
                .from(review)
                .join(review.store, store)
                .where(builder)
                .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0);
    }
}
