package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
    @JoinColumn(name="comment_id", nullable=false)
    private CommentModel comment;

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
}
