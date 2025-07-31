package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="lessons")
public class LessonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id", nullable=false)
    private TeacherModel teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false)
    private MemberModel member;

    @Column(name="date")
    private LocalDateTime date;
}
