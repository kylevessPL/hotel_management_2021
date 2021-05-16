package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.infrastructure.model.RefreshTokensEntity;

import java.util.Optional;

public interface RefreshTokensEntityDao extends JpaRepository<RefreshTokensEntity, Integer> {

    Optional<RefreshTokensEntity> deleteByToken(String token);
}
