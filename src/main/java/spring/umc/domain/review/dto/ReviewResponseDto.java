package spring.umc.domain.review.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ReviewResponseDto {
    private Long id;
    private String content;
    private Integer rating;
    private String storeName;

    @QueryProjection //QReviewResponseDto 생성을 위해 꼭 있어야한다
    public ReviewResponseDto(Long id, String content, Integer star, String storeName) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.storeName = storeName;
    }
}
