package pl.piasta.hotel.domainmodel.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public final class RefreshToken {

    private final String token;
    private final LocalDateTime expiryDate;
    private final String subject;
}
