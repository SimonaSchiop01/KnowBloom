package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="members")
public class MemberModel extends UserModel{
    @OneToMany(
        mappedBy = "member",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<LessonModel> lessons = new HashSet<>();
}
