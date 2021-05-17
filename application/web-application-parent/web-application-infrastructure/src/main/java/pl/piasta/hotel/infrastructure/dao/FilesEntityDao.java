package pl.piasta.hotel.infrastructure.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piasta.hotel.infrastructure.model.FilesEntity;

public interface FilesEntityDao extends JpaRepository<FilesEntity, Integer> {
}
