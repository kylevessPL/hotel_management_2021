package pl.piasta.hotel.infrastructure.model;

import lombok.Getter;
import lombok.Setter;
import pl.piasta.hotel.domainmodel.security.utils.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class RolesEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roles_generator")
    @SequenceGenerator(name = "roles_generator", sequenceName = "seq_roles", allocationSize = 1)
    private Integer id;
    @Column(name = "name", unique = true, nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Role name;

}
