package pl.piasta.hotel.domain.security;

import pl.piasta.hotel.domainmodel.security.RefreshTokenCommand;
import pl.piasta.hotel.domainmodel.security.RefreshTokenInfo;
import pl.piasta.hotel.domainmodel.security.TokenInfo;
import pl.piasta.hotel.domainmodel.security.UserLoginCommand;
import pl.piasta.hotel.domainmodel.security.UserRegisterCommand;

public interface AuthService {

    TokenInfo authenticateUser(UserLoginCommand command);
    void registerUser(UserRegisterCommand command);
    RefreshTokenInfo refreshToken(RefreshTokenCommand command);
}
