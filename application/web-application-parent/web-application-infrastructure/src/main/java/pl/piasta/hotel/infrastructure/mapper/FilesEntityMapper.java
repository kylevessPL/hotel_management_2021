package pl.piasta.hotel.infrastructure.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.piasta.hotel.domainmodel.users.AvatarImage;
import pl.piasta.hotel.infrastructure.model.FilesEntity;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilesEntityMapper {

    public Optional<AvatarImage> mapToAvatarImage(Optional<FilesEntity> file) {
         return file.map(e -> new AvatarImage(e.getName(), e.getType(), e.getData()));
    }
}
