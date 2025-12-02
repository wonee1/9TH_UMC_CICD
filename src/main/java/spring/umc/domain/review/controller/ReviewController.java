package spring.umc.domain.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.umc.domain.review.dto.ReviewResponseDto;
import spring.umc.domain.review.service.ReviewService;


@RestController
@RequestMapping("/api/my/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 내가 작성한 리뷰 목록 조회 API
     *  - /api/my/reviews : 내가 작성한 리뷰 목록 조회
     *  - 필터링 : 가게명(storeName), 별점대(ratingGroup)
     *  - 페이징 : Pageable (page, size)
     */
    @GetMapping
    public ResponseEntity<Page<ReviewResponseDto>> getMyReviews(
            @RequestParam(name = "userId") Long userId, // 현재는 임시로 userId 직접 받음 . . 나중에 로그인 로직 설정하면 수정하기
            @RequestParam(name = "storeName", required = false) String storeName, //가게 이름
            @RequestParam(name = "ratingGroup", required = false) Integer ratingGroup, //별점
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<ReviewResponseDto> reviews = reviewService.getMyReviews(userId, storeName, ratingGroup, pageable);
        return ResponseEntity.ok(reviews);
    }
}
