package test_review.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import test_review.DAO.ReviewDAO;
import test_review.DTO.ReviewDTO;
import test_review.Service.ReviewService;

import java.io.IOException;
import java.util.List;

@RestController //RestAPI 컨트롤러 사용
@RequestMapping ("/api/reviews/")// 요청에 대한 메소드를 url
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    // 리뷰 생성
    @PostMapping("/create")
    public ResponseEntity<ReviewDTO> createReview(@ModelAttribute ReviewDTO reviewDTO,
                                                  @RequestParam("multipartFile")MultipartFile multipartFile) throws IOException {
        ReviewDTO savedReview = reviewService.createReview(reviewDTO, multipartFile);
        return ResponseEntity.ok(savedReview);
    }

    // 모든 리뷰 조회
    @GetMapping("/list")
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviews);
    }

    // 하나의 아이디 조회
    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        return reviewService.getReviewById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //리뷰 수정(업데이트)
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id,
                                                  @ModelAttribute ReviewDTO reviewDTO,
                                                  @RequestParam(value = "multipartFile", required = false)
                                                  MultipartFile multipartFile) throws IOException {
        ReviewDTO updatedReview = reviewService.updateReview(id, reviewDTO , multipartFile);
        if (updatedReview != null) {
            return ResponseEntity.ok(updatedReview);
        }
        return ResponseEntity.notFound().build();
    }

    //리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDTO> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}

