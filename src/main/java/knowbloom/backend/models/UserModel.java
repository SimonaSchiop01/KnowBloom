package knowbloom.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Setter
@Getter
@ToString
@Inheritance(strategy = InheritanceType.JOINED) // Creează tabele separate pentru subclase
@Table(name="users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(unique=true, nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(name="phone_number", unique = true, nullable = false)
    private String phoneNumber;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private Set<NotificationModel> notifications = new HashSet<>();

    public void addNotification(NotificationModel model) {
        this.notifications.add(model);
        model.setUser(this);
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleModel> roles = new HashSet<>();

//    public void addRole(RoleModel model) {
//        this.roles.add(model);
//        model.setUser(this);
//    }

    public void addRole(RoleModel role) {
        if (role != null) {
            if (this.roles == null) {
                this.roles = new HashSet<>();
            }
            this.roles.add(role);
        }
    }

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ReplyModel> reply = new HashSet<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
