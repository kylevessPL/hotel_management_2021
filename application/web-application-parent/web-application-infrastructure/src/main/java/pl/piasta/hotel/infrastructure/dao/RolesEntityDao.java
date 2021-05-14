package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.domainmodel.security.utils.Role;
import pl.piasta.hotel.infrastructure.model.RolesEntity;

public interface RolesEntityDao extends JpaRepository<RolesEntity, Integer> {

    RolesEntity getByName(Role name);
}
