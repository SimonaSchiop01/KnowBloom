package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="replies")
public class ReplyModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable=false)
    private String title;

    @Column(nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="review_id", nullable=false)
    private ReviewModel review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private UserModel user;
    @OneToMany(
        mappedBy = "reply",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<ReplyReactModel> reacts = new HashSet<>();

    public void addReact(ReplyReactModel model) {
        this.reacts.add(model);
        model.setReply(this);
    }

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
