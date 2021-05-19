package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.infrastructure.model.DiscountsEntity;

import java.util.Optional;

public interface DiscountsEntityDao extends JpaRepository<DiscountsEntity, Integer> {

    Optional<DiscountsEntity> findByCode(String code);
    boolean deleteByCode(String code);
    boolean existsByCode(String code);
}
