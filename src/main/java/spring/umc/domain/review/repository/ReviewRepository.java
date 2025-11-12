package spring.umc.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.umc.domain.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
}
