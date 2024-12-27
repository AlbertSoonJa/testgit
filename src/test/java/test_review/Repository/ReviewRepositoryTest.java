package test_review.Repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test_review.DAO.ReviewDAO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    // 리뷰 생성
    @Test
    void 리뷰생성() {
        ReviewDAO reviewDAO = ReviewDAO.builder()
                .comment("sosoooooo")
                .userId(126L)
//                .reviewId(111L) //  reviewId는 JPA가 자동으로 생성하도록 설정해야 합니다. @GeneratedValue가 적용된 경우 reviewId를 수동으로 설정x
                .rating(3)
                .reviewDate(LocalDate.now())
                .uploadPicture("fubao2.jpg")
                .build();

        //데이터베이스에 저장
        ReviewDAO saved = reviewRepository.save(reviewDAO);

        //생성한 ReviewDAO 객체 사용 log 안 찍혀서 사용했음
//     System.out.println("Review created: " + saved);

        log.info("Review created: {}", saved);
    }

    // 특정 아이디 하나만 조회
    @Test
    void 리뷰조회() {
        Long reviewId = 1L; // 특정 ID
        Optional<ReviewDAO> review = reviewRepository.findById(reviewId);

        if (review.isPresent()) {
            System.out.println("Review found: " + review.get());
        } else {
            System.out.println("Review with ID " + reviewId + " not found.");
        }
        log.info("Review with ID {} not found.", reviewId);
    }

    // 모든 리뷰 전체조회
    @Test
    void 모든리뷰조회() {
        List<ReviewDAO> allreviews = reviewRepository.findAll();

        if (!allreviews.isEmpty()) {
            log.info("All reviews:");
            allreviews.forEach(review -> log.info(allreviews.toString()));
        } else {
            log.info("No reviews found.");
        }
    }

}

//    reviewId;
//    private Long userId;
//    private Long productId;
//    private int rating;
//    private String comment;
//    private LocalDate reviewDate;
//    private String uploadPicture;



