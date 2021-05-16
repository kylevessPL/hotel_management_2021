package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.infrastructure.model.UsersEntity;

import java.util.Optional;

public interface UsersEntityDao extends JpaRepository<UsersEntity, Integer> {

    Optional<UsersEntity> findByUsername(String username);
    UsersEntity getByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
