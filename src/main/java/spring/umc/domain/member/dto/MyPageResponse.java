package spring.umc.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 마이페이지에서 보여줄 사용자 정보 DTO
 */
@Getter
@AllArgsConstructor
public class MyPageResponse {
    private String nickname;
    private String email;
    private Integer point;   // User 엔티티의 point 컬럼 사용
    private Long reviewCount;
    private Long inquiryCount;
}
