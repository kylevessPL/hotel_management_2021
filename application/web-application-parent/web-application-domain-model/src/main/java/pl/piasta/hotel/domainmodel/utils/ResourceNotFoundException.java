package pl.piasta.hotel.domainmodel.utils;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends ApplicationException {

    public ResourceNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
