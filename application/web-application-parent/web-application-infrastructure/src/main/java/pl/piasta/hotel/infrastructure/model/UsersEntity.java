package pl.piasta.hotel.infrastructure.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import pl.piasta.hotel.domainmodel.security.utils.AccountStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UsersEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_generator")
    @SequenceGenerator(name = "users_generator", sequenceName = "seq_users", allocationSize = 1)
    private Integer id;
    @Column(name = "username", unique = true, nullable = false, length = 20)
    private String username;
    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;
    @Column(name = "password", nullable = false, length = 120)
    private String password;
    @Type(type="pg-uuid")
    @Column(name = "image_id", length = 36)
    private UUID imageId;
    @Column(name = "status", nullable = false, length = 6)
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.ACTIVE;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> roles = new HashSet<>();

}
