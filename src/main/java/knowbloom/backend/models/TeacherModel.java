package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
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
    private Set<TeacherRateModel> rates = new HashSet<>();

    @OneToMany(
        mappedBy = "teacher",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<LessonModel> lessons = new HashSet<>();

    public void addRate(TeacherRateModel model){
        this.rates.add(model);
        model.setTeacher(this);
    }

    public void addLesson(LessonModel model){
        this.lessons.add(model);
        model.setTeacher(this);
    }
}