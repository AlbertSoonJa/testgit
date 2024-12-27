package test_review.DTO;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Data
public class ReviewDTO {

    private Long reviewId;
    private Long userId;
    private Long productId;
    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private String uploadPicture;

    public void setTitle(String sampleTitle) {
    }

    public void setContent(String sampleContent) {
    }

    public String getTitle() {
        return null;
    }
}


