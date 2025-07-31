package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name="comments")
public class CommentModel {
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
        mappedBy = "comment",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<ReplyModel> replies = new HashSet<>();

    public void addReply(ReplyModel model) {
        this.replies.add(model);
        model.setComment(this);
    }

    @OneToMany(
        mappedBy = "comment",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<CommentReactModel> reacts = new ArrayList<>();

    public void addReact(CommentReactModel model) {
        this.reacts.add(model);
        model.setComment(this);
    }
}
