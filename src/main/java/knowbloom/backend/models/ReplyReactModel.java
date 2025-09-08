package knowbloom.backend.models;

import jakarta.persistence.*;
import knowbloom.backend.enums.ReactType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(
    name="replies_react",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"reply_id", "react"})
    }
)
public class ReplyReactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "reply_id", nullable = false)
    private ReplyModel reply;

    @Enumerated(EnumType.STRING)
    private ReactType react;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
}