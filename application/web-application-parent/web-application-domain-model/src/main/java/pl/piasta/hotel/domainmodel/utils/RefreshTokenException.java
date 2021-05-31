package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;

@Getter
public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException() {
        super();
    }
}
