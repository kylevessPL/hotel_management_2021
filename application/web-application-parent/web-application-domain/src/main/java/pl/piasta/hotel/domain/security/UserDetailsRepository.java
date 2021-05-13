package pl.piasta.hotel.domain.security;

import pl.piasta.hotel.domainmodel.security.UserInfo;

import java.util.Optional;

public interface UserDetailsRepository {

    Optional<UserInfo> findUserByUsername(String username);

}
