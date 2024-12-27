package test_review.DAO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column
    private Long userId;

    @Column
    private Long productId;

    @Column
    private int rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column
    private LocalDate reviewDate;

    @Column(nullable = true)
    private String uploadPicture;

}