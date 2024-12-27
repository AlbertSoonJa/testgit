package test_review.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import test_review.DAO.ReviewDAO;
import test_review.DTO.ReviewDTO;
import test_review.Repository.ReviewRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final String uploadDir = "uploads/"; // 업로드 파일 저장 경로

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //리뷰 생성
    public ReviewDTO createReview(ReviewDTO reviewDTO, MultipartFile multipartFile) throws IOException{
        ReviewDAO reviewDAO = toEntity(reviewDTO); // Dto > entity 변환
        reviewDAO.setReviewDate(LocalDate.now()); // 리뷰 생성날짜

        // 업로드 사진 처리
        if(multipartFile != null && !multipartFile.isEmpty()) {
            String fileName = multipartFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, multipartFile.getBytes());
            reviewDAO.setUploadPicture(filePath.toString());
        }

        ReviewDAO saveReview = reviewRepository.save(reviewDAO);
        return  toDTO(saveReview);// entity > dto 변환하여 반환
    }

    // 모든 리뷰 조회
    public List<ReviewDTO> getAllReviews() {
        List<ReviewDAO> reviews = reviewRepository.findAll();
        return reviews.stream().map(this::toDTO).toList(); // entity dto 변환
    }

    //특정 리뷰 조회
    public Optional<ReviewDTO> getReviewById (Long id) {
        return reviewRepository.findById(id).map(this::toDTO); // entity dto 변환
    }

    // 리뷰수정(업데이트)
    public ReviewDTO updateReview(Long id, ReviewDTO reviewDTO, MultipartFile multipartFile) throws  IOException {
        Optional<ReviewDAO> optionalReviewDAO = reviewRepository.findById(id);
        if(optionalReviewDAO.isPresent()) {
            ReviewDAO reviewDAO = optionalReviewDAO.get();
            reviewDAO.setReviewId(reviewDTO.getReviewId());
            reviewDAO.setProductId(reviewDTO.getProductId());
            reviewDAO.setRating(reviewDTO.getRating());
            reviewDAO.setComment(reviewDTO.getComment());
            reviewDAO.setReviewDate(reviewDTO.getReviewDate());

            // 업로도된 사진 처리
            if (multipartFile != null && !multipartFile.isEmpty()){
                String fileName = multipartFile.getOriginalFilename();
                Path filePath = Paths.get(uploadDir + fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, multipartFile.getBytes());
                reviewDAO.setUploadPicture(filePath.toString());
            }

            ReviewDAO updatedReview = reviewRepository.save(reviewDAO);
            return  toDTO(updatedReview); // entitu > dto
        }
        return null;
    }

    // 리뷰 삭제
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    //DTO > Entity 변환
    private ReviewDAO toEntity(ReviewDTO reviewDTO){
        ReviewDAO reviewDAO = new ReviewDAO();
        reviewDAO.setReviewId(reviewDTO.getReviewId());
        reviewDAO.setUserId(reviewDTO.getUserId());
        reviewDAO.setProductId(reviewDTO.getProductId());
        reviewDAO.setRating(reviewDTO.getRating());
        reviewDAO.setComment(reviewDTO.getComment());
        reviewDAO.setReviewDate(reviewDTO.getReviewDate());
        reviewDAO.setUploadPicture(reviewDTO.getUploadPicture());
        return reviewDAO;
    }

    private ReviewDTO toDTO(ReviewDAO reviewDAO){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewId(reviewDAO.getReviewId());
        reviewDTO.setUserId(reviewDAO.getUserId());
        reviewDTO.setProductId(reviewDAO.getProductId());
        reviewDTO.setRating(reviewDAO.getRating());
        reviewDTO.setComment(reviewDAO.getComment());
        reviewDTO.setReviewDate(reviewDAO.getReviewDate());
        reviewDTO.setUploadPicture(reviewDAO.getUploadPicture());
        return reviewDTO;
    }
}