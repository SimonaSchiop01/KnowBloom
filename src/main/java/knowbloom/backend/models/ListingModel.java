package knowbloom.backend.models;

import jakarta.persistence.*;
import knowbloom.backend.enums.LessonType;
import knowbloom.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name="listings")
public class ListingModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id", nullable=false)
    private TeacherModel teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="subject_id", nullable=false)
    private SubjectModel subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="level_id", nullable=false)
    private LevelModel level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="city_id", nullable=false)
    private CityModel city;

    @OneToMany(
        mappedBy = "listing",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private Set<LessonModel> lessons = new HashSet<>();

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private LessonType lessonType;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private boolean isActive;
}
