package spring.umc.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 마이페이지 조회용 DTO
 */
@Getter
@AllArgsConstructor
public class MyPageResponse {
    private String nickname;
    private String email;
    private Integer point;
    private Long reviewCount;
}
