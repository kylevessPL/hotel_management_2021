package pl.piasta.hotel.infrastructure.mapper;

import org.springframework.stereotype.Component;
import pl.piasta.hotel.domainmodel.security.RefreshToken;
import pl.piasta.hotel.infrastructure.model.RefreshTokensEntity;

import java.util.Optional;

@Component
public class RefreshTokensEntityMapper {

    public Optional<RefreshToken> mapToTokenInfo(Optional<RefreshTokensEntity> tokensEntity) {
        return tokensEntity.map(e -> new RefreshToken(e.getToken(), e.getExpiryDate(), e.getUser().getUsername()));
    }
}
