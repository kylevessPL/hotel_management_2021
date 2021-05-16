package pl.piasta.hotel.domainmodel.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public final class TokenInfo {

    private final String accessToken;
    private final String refreshToken;
    private final String type = "Bearer";
    private final int expires;
    private final Integer id;
    private final String username;
    private final String email;
    private final Set<String> roles;
}
