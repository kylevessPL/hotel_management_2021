package pl.piasta.hotel.domain.security;

import pl.piasta.hotel.domainmodel.security.RefreshToken;
import pl.piasta.hotel.domainmodel.security.UserInfo;
import pl.piasta.hotel.domainmodel.security.utils.Role;

import java.util.Optional;
import java.util.Set;

public interface AuthRepository {

    Optional<UserInfo> findUserByUsername(String username);
    void saveUser(String username, String email, String password, Set<Role> roles);
    boolean userExistsByUsername(String username);
    boolean userExistsByEmail(String email);
    void saveRefreshToken(RefreshToken refreshToken);
    Optional<RefreshToken> deleteRefreshTokenByToken(String token);
}
