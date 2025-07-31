package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

// foloseste @Getter, @Setter in loc de a face getters si setters de asemenea, poti sa folosesti si
// @ToString, dar grija mare  sa nu ai recursie infinita, de exemplu, la RoleModel, pui @ToString
// dar pui     @ToString.Exclude la  setul de users

@Entity
@Getter
@Setter
@Table(name="categories")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique=true, nullable = false)
    private String name;

    @OneToMany(
        mappedBy = "category",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )

    private Set<SubjectModel> subjects = new HashSet<>();

    public void addSubject(SubjectModel model) {
        this.subjects.add(model);
        model.setCategory(this);
    }

}
