package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name="comments")
public class ReviewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Integer rating;

    @OneToMany(
        mappedBy = "review",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<ReplyModel> replies = new HashSet<>();

    public void addReply(ReplyModel model) {
        this.replies.add(model);
        model.setReview(this);
    }

    @OneToMany(
        mappedBy = "comment",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<ReviewReactModel> reacts = new ArrayList<>();

    public void addReact(ReviewReactModel model) {
        this.reacts.add(model);
        model.setComment(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false)
    private MemberModel member;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
