package pl.piasta.hotel.domainmodel.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class RefreshTokenInfo {

    private final String accessToken;
    private final String refreshToken;
    private final String tokenType = "Bearer";
    private final int expires;
}
