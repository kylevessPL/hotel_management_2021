package pl.piasta.hotel.domainmodel.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public final class UserLoginInfo {

    private final String token;
    private final Integer id;
    private final String username;
    private final String email;
    private final Set<String> roles;
}
