package knowbloom.backend.models;

import jakarta.persistence.*;
import knowbloom.backend.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name="roles")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true)
    private Role name;

    // de facut relatie de many to many cu users, de pus de asemenea @ToString.Exclude si @ToString pe RoleModel
    @ManyToMany(mappedBy = "roles")
    @ToString.Exclude
    private Set<UserModel> users = new HashSet<>();
}
