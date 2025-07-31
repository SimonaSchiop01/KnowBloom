package knowbloom.backend.models;

import jakarta.persistence.*;
import knowbloom.backend.enums.ReactType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="comments_reacts")
public class CommentReactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="comment_id", nullable=false)
    private CommentModel comment;

    @Enumerated(EnumType.STRING)
    private ReactType react;
}