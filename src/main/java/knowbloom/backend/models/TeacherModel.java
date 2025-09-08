package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name="teachers")
public class TeacherModel extends UserModel{
    @OneToMany(
        mappedBy = "teacher",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<LessonModel> lessons = new HashSet<>();

    public void addLesson(LessonModel model){
        this.lessons.add(model);
        model.setTeacher(this);
    }

    @OneToMany(
        mappedBy = "teacher",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<ListingModel> listings = new HashSet<>();

    @Column(unique = true, nullable = false)
    private String bio;

    @Column(unique = true, nullable = false)
    private String cv;
}