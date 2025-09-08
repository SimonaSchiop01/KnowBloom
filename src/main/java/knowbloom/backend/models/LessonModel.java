package knowbloom.backend.models;

import jakarta.persistence.*;
import knowbloom.backend.enums.Role;
import knowbloom.backend.enums.Status;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name="lessons")
public class LessonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id", nullable=false)
    @ToString.Exclude
    private TeacherModel teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable=false)
    @ToString.Exclude
    private MemberModel member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="listing_id", nullable=false)
    @ToString.Exclude
    private ListingModel listing;

    @Column(name="date")
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false, unique = true)
    private Status status;
}
