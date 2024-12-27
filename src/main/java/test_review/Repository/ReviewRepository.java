package test_review.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test_review.DAO.ReviewDAO;
import test_review.DTO.ReviewDTO;

@Repository
public interface ReviewRepository  extends JpaRepository <ReviewDAO, Long> {
}
