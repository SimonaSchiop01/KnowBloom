package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="teacher_rates")
public class TeacherRateModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="rate", nullable = false)
    private Integer rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id", nullable=false)
    private TeacherModel teacher;
}
